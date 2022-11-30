package Model

import Http.HttpResponse
import Http.Result
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.koin.core.scope.Scope


interface ResponseView {
    fun getResponse(data: String)
}

class RequestViewModel(
    private val httpResponse: HttpResponse
) {
    private val _myState = MutableStateFlow("0")
    val myState: StateFlow<String> = _myState.asStateFlow()

    suspend fun getSomeValue() {
        flow {
            for (i in 1..100) {
                delay(10)
                emit("" + i)
            }
        }.flowOn(Dispatchers.IO).collect {
            _myState.value = it
        }
    }

    suspend fun login(responseView: ResponseView) {
        val result = arrayListOf<Int>()
        for (index in 1..10) {
            result.add(index)
        }

        result.asFlow().flatMapMerge {
            flow {
                val ret = httpResponse.getHttp()
                emit(it)
            }.flowOn(Dispatchers.IO)
        }.collect {
            responseView.getResponse("" + it)
//                when (it) {
//                    is Result.onSuccess<String> -> {
//                        responseView.getResponse(it.data)
//                    }
//
//                    else -> {
//
//                    }
//                }
        }

//        while (true) {
//            val job = remember.launch {
//                val ret = httpResponse.getHttp()
//                when (ret) {
//                    is Result.onSuccess<String> -> {
//                        responseView.getResponse(ret.data)
//                    }
//
//                    else -> {
//
//                    }
//                }
//            }
//            job.join()
//        }
    }
}