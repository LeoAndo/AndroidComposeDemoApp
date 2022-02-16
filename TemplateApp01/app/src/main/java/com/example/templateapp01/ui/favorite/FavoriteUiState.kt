package com.example.templateapp01.ui.favorite

import com.example.templateapp01.domain.model.TodoData

internal sealed interface FavoriteUiState {
    object Initial : FavoriteUiState
    data class UpdateTodoList(val todoList: List<TodoData>) : FavoriteUiState
    data class Failure(val errorMessage: String) : FavoriteUiState
}