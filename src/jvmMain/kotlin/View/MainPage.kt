package View

import Http.HttpResponse
import Model.RequestViewModel
import Model.ResponseView
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.launchApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.awt.SystemColor.text
import kotlin.concurrent.thread

val model = RequestViewModel(HttpResponse())

@Composable
@Preview
fun MainPage() {

    var txt by remember {
        mutableStateOf("0")
    }

    val coroutine = rememberCoroutineScope()

    model.myState.onEach {
        //窗口每次被加载就会重新获取一遍数据 不知道为什么 但是符合我的需求
        println("数据更新了")
        txt = it
    }.launchIn(coroutine)

    Button(onClick = {
        coroutine.launch {
            model.getSomeValue()
        }
    }) {
        Text("数据得到了: ${txt}")
    }

// 多协程并发
//    coroutine.launch {
//        model.login(object : ResponseView {
//            override fun getResponse(data: String) {
//                println("log ${data}")
//                text += "1"
//            }
//        })
//    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val state = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
        ) {
            items(1000) {
                Card(
                    elevation = 3.dp,
                    modifier = Modifier.fillParentMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        .height(65.dp)
                ) {
                    Text("${it + 1} item was created")
                }
                if (it == 999)
                    Spacer(modifier = Modifier.height(10.dp))
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(
                scrollState = state
            )
        )
    }
}