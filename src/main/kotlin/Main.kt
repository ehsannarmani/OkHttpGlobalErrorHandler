package ir.ehsannarmani

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

fun main() {
    val client = OkHttpClient.Builder()
        .addInterceptor(GlobalErrorHandlerInterceptor(::handleError))
        .build()

    val retrofit = Retrofit
        .Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(client)
        .build()

    val api = retrofit.create(Api::class.java)

    api.getTodo().enqueue(object :Callback<String>{
        override fun onResponse(call: Call<String>, response: Response<String>) {
            println("Success: ${response.body()}")
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            println("Failure: ${t.message}")
        }
    })

}

fun handleError(error: GlobalError){
    when(error){
        is GlobalError.ClientError->{
            println("Client Error Caught! => ${error.error} / ${error.statusCode}")
        }
        is GlobalError.ServerError->{
            println("Server Error Caught! => ${error.error} / ${error.statusCode}")
        }
    }
}