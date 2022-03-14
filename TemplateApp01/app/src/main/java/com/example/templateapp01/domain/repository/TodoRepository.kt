package com.example.templateapp01.domain.repository

import com.example.templateapp01.domain.model.TodoData
import kotlinx.coroutines.flow.Flow
import java.util.*

internal interface TodoRepository {
    suspend fun addTodoData(vararg todoData: TodoData)
    suspend fun findTodoDataById(id: Int): TodoData
    suspend fun deleteAllTodoItems()
    suspend fun updateTodoData(vararg todoData: TodoData)
    fun getTodoList(): Flow<List<TodoData>>
    suspend fun getCompletedTodoList(): List<TodoData>
    suspend fun getInCompleteTodoList(): List<TodoData>
    suspend fun getBetweenDatesTodoList(from: Date, to: Date): List<TodoData>
}