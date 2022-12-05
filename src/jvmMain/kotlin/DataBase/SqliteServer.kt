package DataBase

import DataBase.Entity.AppList
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SqliteServer {

    /**
     * 数据库初始化函数
     */
    fun init() {
        val session = Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                AppList
            )
        }

        initializationDataBaseItems()
        println("数据库初始化结束。")
    }

    /**
     * 在这里初始化数据库内的表数据
     */
    private fun initializationDataBaseItems() {
        val size = transaction { AppList.selectAll().count() }
        if (size <= 0) {
            for (i in 1..10) {
                transaction {
                    AppList.insert {
                        it[title] = "$i App"
                        it[version] = "1.0"
                    }
                } get AppList.id
            }
        }
    }
}