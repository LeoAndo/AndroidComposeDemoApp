package com.example.templateapp01.data.repository.impl

import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.domain.repository.TodoRepository
import com.example.templateapp01.data.room.dao.TodoDao
import com.example.templateapp01.data.room.entity.TodoEntity
import com.example.templateapp01.data.room.entity.toTodoData
import com.example.templateapp01.data.room.entity.toTodoDataList
import com.example.templateapp01.data.safeCall
import com.example.templateapp01.di.IoDispatcher
import com.example.templateapp01.domain.model.TodoData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

internal class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TodoRepository {
    override suspend fun addTodoData(vararg todoData: TodoData) {
        val todoEntities = todoData.mapIndexed { _, entity ->
            TodoEntity(
                id = entity.id,
                title = entity.title,
                memo = entity.memo,
                completionDate = entity.completionDate,
                registrationDate = entity.registrationDate
            )
        }.toTypedArray()
        safeCall(dispatcher) {
            todoDao.insertTodoData(*todoEntities)
        }
    }

    override suspend fun findTodoDataById(id: Int): SafeResult<TodoData> {
        return safeCall(dispatcher) { todoDao.findTodoDataById(id).toTodoData() }
    }

    override suspend fun deleteAllTodoItems() {
        safeCall(dispatcher) {
            todoDao.deleteAllTodoItems()
        }
    }

    override suspend fun updateTodoData(vararg todoData: TodoData) {
        val todoEntities = todoData.mapIndexed { _, entity ->
            TodoEntity(
                id = entity.id,
                title = entity.title,
                memo = entity.memo,
                completionDate = entity.completionDate,
                registrationDate = entity.registrationDate
            )
        }.toTypedArray()
        safeCall(dispatcher) { todoDao.updateTodoData(*todoEntities) }
    }

    override fun getTodoList(): Flow<List<TodoData>> {
        return todoDao.findTodoListAsFlow().map { it.toTodoDataList() }.flowOn(dispatcher)
    }

    override suspend fun getCompletedTodoList(): SafeResult<List<TodoData>> {
        return safeCall(dispatcher) { todoDao.findCompletedTodoList().toTodoDataList() }
    }

    override suspend fun getInCompleteTodoList(): SafeResult<List<TodoData>> {
        return safeCall(dispatcher) { todoDao.findInCompleteTodoList().toTodoDataList() }
    }

    override suspend fun getBetweenDatesTodoList(
        from: Date,
        to: Date
    ): SafeResult<List<TodoData>> {
        return safeCall(dispatcher) { todoDao.findBetweenDatesTodoList(from, to).toTodoDataList() }
    }
}