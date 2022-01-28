package com.example.templateapp01.domain.usecase

import android.util.Log
import com.example.templateapp01.data.SafeResult
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
            when (val result = todoRepository.findTodoDataById(todoDataId)) {
                is SafeResult.Error -> {
                    Log.e("TodoDoneUseCase", "error: " + result.errorResult.localizedMessage)
                }
                is SafeResult.Success -> {
                    val updateData = result.data.copy(completionDate = Date())
                    todoRepository.updateTodoData(updateData)
                }
            }
        }
    }
}