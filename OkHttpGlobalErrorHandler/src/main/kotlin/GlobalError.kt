package ir.ehsannarmani
sealed class GlobalError{
    data class ClientError(val error: String,val statusCode:Int):GlobalError()
    data class ServerError(val error: String,val statusCode:Int):GlobalError()
}