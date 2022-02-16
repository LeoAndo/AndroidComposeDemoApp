package com.example.templateapp01.domain.repository

import com.example.templateapp01.domain.model.TodoData
import kotlinx.coroutines.flow.Flow
import java.util.*

internal interface TodoRepository {
    suspend fun addTodoData(vararg todoData: TodoData)
    suspend fun findTodoDataById(id: Int): Result<TodoData>
    suspend fun deleteAllTodoItems()
    suspend fun updateTodoData(vararg todoData: TodoData)
    fun getTodoList(): Flow<List<TodoData>>
    suspend fun getCompletedTodoList(): Result<List<TodoData>>
    suspend fun getInCompleteTodoList(): Result<List<TodoData>>
    suspend fun getBetweenDatesTodoList(from: Date, to: Date): Result<List<TodoData>>
}