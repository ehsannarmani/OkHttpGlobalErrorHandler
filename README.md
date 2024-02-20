# OkHttp Global Error Handler
### A simple interceptor to catch client and server errors.
### Using On OkHttp Client:
```kotlin
val client = OkHttpClient.Builder()
    .addInterceptor(GlobalErrorHandlerInterceptor{ error->
        when(it){
            is GlobalError.ClientError->{
                println("Client Error Caught! => ${error.error} / ${error.statusCode}")
            }
            is GlobalError.ServerError->{
                println("Server Error Caught! => ${error.error} / ${error.statusCode}")
            }
        }
    })
    .build()
```
### Using With Retrofit:
> [!NOTE]
> add GlobalErrorHandlerInterceptor like previous sample and set that client as retrofit client.
```kotlin
val retrofit = Retrofit
    .Builder()
    .baseUrl("https://your_beautiful_base_url.com/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(client)
    .build()
```
### Ignoring Global Error Handler On Specify EndPoints:
> Ignoring All Errors:
```kotlin
interface Api {
    @GET("todos/1")
    @IgnoreGlobalErrorHandling(ignoreStrategy = AllErrorsStrategy::class)
    fun getTodo():Call<String>
}
```
> Ignoring Client Errors:
```kotlin
interface Api {
    @GET("todos/1")
    @IgnoreGlobalErrorHandling(ignoreStrategy = ClientErrorsStrategy::class)
    fun getTodo():Call<String>
}
```
> Ignoring Server Errors:
```kotlin
interface Api {
    @GET("todos/1")
    @IgnoreGlobalErrorHandling(ignoreStrategy = ServerErrorsStrategy::class)
    fun getTodo():Call<String>
}
```
> Ignoring Just Some Status Codes:
```kotlin
interface Api {
    @GET("todos/1")
    @IgnoreGlobalErrorHandling(
        ignoreStrategy = StatusCodeStrategy::class,
        ignoreStatusCodes = [422,501]
    )
    fun getTodo():Call<String>
}
```
### More complete and better use with koin & dependency injections:
```kotlin
startKoin{
    single{
        Channel<GlobalError>()
    }
    single{
        val client = OkHttpClient.Builder()
            .addInterceptor(GlobalErrorHandlerInterceptor{ error->
                val injectedChannel:Channel<GlobalError> = get()
                scope.launch{
                    injectedChannel.send(error)
                }
            })
            .build()
        Retrofit
            .Builder()
            ...
            .client(client)
            .build()
    }
}
```
#### In View Side or etc:
``` kotlin
class MainActivity: AppCompatActivity{
    val injectedChannel:Channel<GlobalError> by inject()
    
    override fun onCreate(savedInstanceState: Bundle?){
        ...
        injectedChannel.receiveAsFlow().collect { error ->
            when(error){
                is GlobalError.ClientError->{
                    if(error.statusCode == 401){
                        // Show toast
                        // logout
                        // navigate to login screen
                    }
                }
                is GlobalError.ServerError->{
                    // show server error
                }
            }
        }
    }
}
```
> In this example, Any api of yours that gives a status code of 401 will cause the user to log out and navigate to log in screen.

## Setup
[![](https://jitpack.io/v/ehsannarmani/OkHttpGlobalErrorHandler.svg)](https://jitpack.io/#ehsannarmani/OkHttpGlobalErrorHandler)
```kotlin
repositories{
    ...
    maven("https://jitpack.io")
}
dependencies{
    implementation("com.github.ehsannarmani:OkHttpGlobalErrorHandler:latest")
}
```

