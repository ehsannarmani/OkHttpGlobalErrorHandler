package ir.ehsannarmani

import okhttp3.Interceptor
import okhttp3.Response

class GlobalErrorHandlerInterceptor(private val onCatch: (GlobalError) -> Unit) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return GlobalErrorHandler(
            request = request,
            response = response
        )
            .handle(onCatch = onCatch)
    }

}