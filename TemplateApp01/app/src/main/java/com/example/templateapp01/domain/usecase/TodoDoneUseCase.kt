package com.example.templateapp01.domain.usecase

import com.example.templateapp01.domain.repository.TodoRepository
import com.example.templateapp01.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

internal class TodoDoneUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(todoDataId: Int) {
        return withContext(dispatcher) {
            todoRepository.findTodoDataById(todoDataId)
                .fold(onSuccess = {
                    val updateData = it.copy(completionDate = Date())
                    todoRepository.updateTodoData(updateData)
                }, onFailure = {})
        }
    }
}