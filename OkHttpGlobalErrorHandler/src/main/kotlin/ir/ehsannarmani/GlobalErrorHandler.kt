package ir.ehsannarmani

import ir.ehsannarmani.annotation.*
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Invocation

class GlobalErrorHandler(
    private val request: Request,
    private val response: Response,
) {
    private var originalResponse:Response
    private var stringBody:String
    private var body:ResponseBody? = response.body()

    init {
        stringBody = body?.string() ?: ""
        originalResponse = response
            .newBuilder()
            .body(ResponseBody.create(body?.contentType(),stringBody))
            .build()
    }

    fun handle(onCatch:(GlobalError)->Unit):Response{
        val method = request.tag(Invocation::class.java)?.method()
        val annotation = method?.getAnnotation(IgnoreGlobalErrorHandling::class.java)
        val ignoreStrategy = annotation?.ignoreStrategy
        if (ignoreStrategy == AllErrorsStrategy::class)
            return originalResponse
        if (!response.isSuccessful){
            val statusCode = response.code()
            if (ignoreStrategy == StatusCodeStrategy::class && statusCode in annotation.ignoreStatusCodes)
                return originalResponse

            when(statusCode){
                in 400..499->{
                    // client error
                    if (ignoreStrategy == ClientErrorsStrategy::class)
                        return originalResponse

                    onCatch(GlobalError.ClientError(error = stringBody, statusCode))
                }
                in 500..599->{
                    // server error
                    if (ignoreStrategy == ServerErrorsStrategy::class)
                        return originalResponse

                    onCatch(GlobalError.ServerError(error = stringBody, statusCode))
                }
            }
        }
        return originalResponse
    }
}