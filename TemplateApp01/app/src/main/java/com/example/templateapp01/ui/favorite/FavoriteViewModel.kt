package com.example.templateapp01.ui.favorite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateapp01.data.repository.TodoRepository
import com.example.templateapp01.model.TodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    var uiState by mutableStateOf<FavoriteUiState>(FavoriteUiState.Initial)

    init {
        Log.d(LOG_TAG, "init: " + hashCode())
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            // Failure
            uiState = FavoriteUiState.Error(throwable.localizedMessage ?: "error!")
        }) {
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
        viewModelScope.launch {
            todoRepository.addTodoData(todoData)
        }
    }

    fun deleteAllTodoItems() {
        viewModelScope.launch {
            todoRepository.deleteAllTodoItems()
        }
    }

    companion object {
        const val LOG_TAG = "FavoriteViewModel"
    }
}