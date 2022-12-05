import DataBase.SqliteServer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SystemInitialization : KoinComponent {
    val aa: String by inject()
    val sqliteServer: SqliteServer by inject()

    fun initializationSystem() {
        sqliteServer.init()
    }

    override fun toString(): String {
        println(aa)
        return super.toString()
    }
}