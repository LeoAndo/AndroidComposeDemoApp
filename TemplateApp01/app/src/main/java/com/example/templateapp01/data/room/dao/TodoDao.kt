package com.example.templateapp01.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.templateapp01.data.room.entity.TodoEntity
import com.example.templateapp01.data.room.entity.TABLE_NAME_TODO
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
internal interface TodoDao {
    @Insert
    suspend fun insertTodoData(vararg onsenEntity: TodoEntity)

    @Update
    suspend fun updateTodoData(vararg onsenEntity: TodoEntity)

    @Query("SELECT * FROM $TABLE_NAME_TODO")
    fun findTodoList(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE completionDate != null")
    fun findCompletedTodoList(): List<TodoEntity>

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE completionDate == null")
    fun findInCompleteTodoList(): List<TodoEntity>

    @Query("SELECT * FROM $TABLE_NAME_TODO WHERE registrationDate BETWEEN :from AND :to")
    fun findBetweenDatesTodoList(from: Date, to: Date): List<TodoEntity>
}