package com.example.templateapp01.ui.favorite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.domain.repository.TodoRepository
import com.example.templateapp01.domain.usecase.TodoDoneUseCase
import com.example.templateapp01.domain.model.TodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val todoDoneUseCase: TodoDoneUseCase
) : ViewModel() {
    var uiState by mutableStateOf<FavoriteUiState>(FavoriteUiState.Initial)
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // TODO: Failure: whenでエラーパターンによって処理を変える
        uiState = FavoriteUiState.Error(throwable.localizedMessage ?: "error!")
    }

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
        viewModelScope.launch(coroutineExceptionHandler) {
            todoRepository.getTodoList().collect {
                uiState = FavoriteUiState.UpdateTodoList(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(LOG_TAG, "onCleared" + hashCode())
    }

    fun addTodoData(todoData: TodoData) {
        viewModelScope.launch(coroutineExceptionHandler) {
            todoRepository.addTodoData(todoData)
        }
    }

    fun updateTodoData(todoDataId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            todoDoneUseCase(todoDataId)
        }
    }

    fun deleteAllTodoItems() {
        viewModelScope.launch(coroutineExceptionHandler) {
            todoRepository.deleteAllTodoItems()
        }
    }

    companion object {
        const val LOG_TAG = "FavoriteViewModel"
    }
}