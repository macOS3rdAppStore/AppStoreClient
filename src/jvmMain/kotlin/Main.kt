// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import DataBase.SqliteServer
import View.MainPage
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, Compose Kotlin!") }

    MaterialTheme {
        var select by remember { mutableStateOf(0) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("macOS应用")
                    }
                )
            },
            bottomBar = {
                BottomNavigation {
                    BottomNavigationItem(
                        label = { Text("首页") },
                        onClick = { select = 0 },
                        selected = select == 0,
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                "clickme",
                                Modifier.size(ButtonDefaults.IconSize)
                            )
                        }
                    )

                    BottomNavigationItem(
                        label = { Text("大厅") },
                        onClick = { select = 1 },
                        selected = select == 1,
                        icon = {
                            Icon(
                                Icons.Default.Place,
                                "clickme",
                                Modifier.size(ButtonDefaults.IconSize)
                            )
                        }
                    )

                    BottomNavigationItem(
                        label = { Text("我的") },
                        onClick = { select = 2 },
                        selected = select == 2,
                        icon = {
                            Icon(
                                Icons.Default.AccountBox,
                                "clickme",
                                Modifier.size(ButtonDefaults.IconSize)
                            )
                        }
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues = it)
                    .fillMaxSize()
            ) {
                //simulation page switch
                when (select) {
                    0 -> {
                        MainPage()
                    }

                    1 -> {
                        Button(onClick = {
                            text = "Hello, macOS Panel 1!"
                        }) {
                            text = "1"
                            Text(text)
                        }
                    }

                    else -> {
                        Button(onClick = {
                            text = "Hello, macOS! expect 0,1!"
                        }) {
                            text = "2"
                            Text(text)
                        }
                    }
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

            single { SqliteServer() }
            single { SystemInitialization() }
        })
    }

    val systemInitialization: SystemInitialization by inject(SystemInitialization::class.java)

    systemInitialization.apply {
        initializationSystem()
        toString()
    }

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
