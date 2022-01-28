package com.example.templateapp01.domain.repository

import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.domain.model.TodoData
import kotlinx.coroutines.flow.Flow
import java.util.*

internal interface TodoRepository {
    suspend fun addTodoData(vararg todoData: TodoData)
    suspend fun findTodoDataById(id: Int): SafeResult<TodoData>
    suspend fun deleteAllTodoItems()
    suspend fun updateTodoData(vararg todoData: TodoData)
    fun getTodoList(): Flow<List<TodoData>>
    suspend fun getCompletedTodoList(): SafeResult<List<TodoData>>
    suspend fun getInCompleteTodoList(): SafeResult<List<TodoData>>
    suspend fun getBetweenDatesTodoList(from: Date, to: Date): SafeResult<List<TodoData>>
}