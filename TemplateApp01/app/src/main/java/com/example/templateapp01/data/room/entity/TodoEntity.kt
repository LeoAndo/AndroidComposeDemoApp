package com.example.templateapp01.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.templateapp01.model.TodoData
import java.util.*

internal const val TABLE_NAME_TODO = "todo"

@Entity(tableName = TABLE_NAME_TODO)
internal data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val memo: String,
    val completionDate: Date?,
    val registrationDate: Date,
)

internal fun List<TodoEntity>.toTodoDataList(): List<TodoData> {
    return this.mapIndexed { _, entity ->
        TodoData(
            id = entity.id,
            title = entity.title,
            memo = entity.memo,
            completionDate = entity.completionDate,
            registrationDate = entity.registrationDate
        )
    }
}

internal fun TodoEntity.toTodoData(): TodoData =
    TodoData(
        id = id,
        title = title,
        memo = memo,
        completionDate = completionDate,
        registrationDate = registrationDate
    )