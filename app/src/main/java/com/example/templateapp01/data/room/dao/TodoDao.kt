package com.example.templateapp01.data.room.dao

import androidx.room.*
import com.example.templateapp01.data.room.entity.TodoEntity
import com.example.templateapp01.data.room.entity.TABLE_NAME_TODO
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
internal interface TodoDao {
    @Insert
    suspend fun insertTodoData(vararg todoEntity: TodoEntity)

    @Query("DELETE FROM $TABLE_NAME_TODO")
    suspend fun deleteAllTodoItems()

    @Update
    suspend fun updateTodoData(vararg todoEntity: TodoEntity)

    @Query("SELECT * FROM $TABLE_NAME_TODO")
    fun findTodoListAsFlow(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE id = :id")
    fun findTodoDataById(id: Int): TodoEntity

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE completionDate != null")
    suspend fun findCompletedTodoList(): List<TodoEntity>

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE completionDate == null")
    suspend fun findInCompleteTodoList(): List<TodoEntity>

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE registrationDate BETWEEN :from AND :to")
    suspend fun findBetweenDatesTodoList(from: Date, to: Date): List<TodoEntity>
}