// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, Compose Kotlin!") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopAppBar(
                title = { Text("首页") }
            )

            Column(
                modifier = Modifier.padding(
                    start = 5.dp,
                    end = 5.dp,
                    bottom = 5.dp
                )
            ) {
                Button(onClick = {
                    text = "Hello, macOS!"
                }) {
                    Text(text)
                }

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val state = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                    ) {
                        items(1000) {
                            Box(
                                Modifier.fillMaxWidth()
                                    .height(65.dp)
                                    .background(Color(180, 180, 180)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${it + 1} item was created")
                            }

                            if (it != 999)
                                Spacer(modifier = Modifier.height(5.dp))
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
        }
    }
}

fun main() = application {
    startKoin {
        modules(module {
            single {
                val qiuchen = "123123"
                qiuchen//注入到本地依赖
            }
        })
    }

    TestCoroutine().toString()
    val client = OkHttpClient.Builder()
        .build()

    val doc = Jsoup.connect("https://www.baidu.com").get()
    println(doc.title())
    data class User(val name: String, val data: String)
    println(Gson().toJson(User(",", "asdas")))

    Window(
        onCloseRequest = ::exitApplication,
        title = "AppStore",
        state = WindowState(
            width = 500.dp,
            height = 900.dp
        )
    ) {
        App()
    }
}
