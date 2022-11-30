package Http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


sealed class Result<out R> {
    data class onSuccess<out T>(val data: T) : Result<T>()
    data class onException(val error: Exception) : Result<Nothing>()
}

open class HttpResponse {
    suspend fun getHttp():Result<String> {
        return withContext(Dispatchers.IO){
            delay(1000)
            Result.onSuccess("hello")
        }
    }
}