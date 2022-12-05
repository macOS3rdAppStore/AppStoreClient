package DataBase.Entity

import org.jetbrains.exposed.sql.Table

object AppList : Table() {

    val id = integer("id").autoIncrement()
    val title = varchar("title", Int.MAX_VALUE)
    val version = varchar("version", Int.MAX_VALUE)

    override val primaryKey = PrimaryKey(id, name = "tableId")
}