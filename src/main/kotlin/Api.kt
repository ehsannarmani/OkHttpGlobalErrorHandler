package ir.ehsannarmani

import ir.ehsannarmani.annotation.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("todos/1")
    @IgnoreGlobalErrorHandling(ignoreStrategy = AllErrorsStrategy::class)
    fun getTodo():Call<String>

    @POST("todos/2")
    @IgnoreGlobalErrorHandling(ignoreStrategy = ClientErrorsStrategy::class)
    fun getTodo2():Call<String>

    @GET("todos/3")
    @IgnoreGlobalErrorHandling(ignoreStrategy = ServerErrorsStrategy::class)
    fun getTodo3():Call<String>

    @GET("todos/4")
    @IgnoreGlobalErrorHandling(
        ignoreStrategy = StatusCodeStrategy::class,
        ignoreStatusCodes = [422,501]
    )
    fun getTodo4():Call<String>
}