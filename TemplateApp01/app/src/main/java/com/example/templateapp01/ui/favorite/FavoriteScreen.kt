package com.example.templateapp01.ui.favorite

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.templateapp01.R
import com.example.templateapp01.domain.model.TodoData
import com.example.templateapp01.ui.components.AppAlertDialog
import com.example.templateapp01.ui.components.TodoItem
import com.example.templateapp01.ui.theme.TemplateApp01Theme
import java.util.*

// Stateful Composable that depends on ViewModel.
@Composable
internal fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    var titleText by remember { mutableStateOf("") }
    var memoText by remember { mutableStateOf("") }
    var enabledAddButton by remember { mutableStateOf(false) }
    var selectedTodoDataId by remember { mutableStateOf<Int?>(null) }
    var openDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        AddTodoItemContent(
            titleText = titleText,
            memoText = memoText,
            enabledAddButton = enabledAddButton,
            onValueChangeTitleText = {
                titleText = it
                enabledAddButton = memoText.isNotBlank() && titleText.isNotBlank()
            },
            onValueChangeMemoText = {
                memoText = it
                enabledAddButton = memoText.isNotBlank() && titleText.isNotBlank()
            },
            onClickAddTodoItemButton = viewModel::addTodoData,
            onClickDeleteAllTodoItemsButton = viewModel::deleteAllTodoItems
        )
        Spacer(modifier = Modifier.height(12.dp))
        TodoListContent(
            uiState = viewModel.uiState,
            onClickTodoItem = {
                selectedTodoDataId = it
                openDialog = true
            },
        )
        AppAlertDialog(
            openDialog = openDialog,
            titleText = "Dialog Title",
            messageText = "Has TODO ITEM completed?",
            confirmText = "Yes",
            dismissText = "No",
            onDismissRequest = {
                openDialog = false
                selectedTodoDataId = null
            },
            onClickConfirmButton = {
                openDialog = false
                selectedTodoDataId?.let { viewModel.updateTodoData(it) }
                selectedTodoDataId = null
            },
            onClickDismissButton = {
                openDialog = false
                selectedTodoDataId = null
            })
    }
}

// stateless Composable.
@Composable
internal fun AddTodoItemContent(
    titleText: String,
    memoText: String,
    enabledAddButton: Boolean,
    onValueChangeTitleText: (String) -> Unit,
    onValueChangeMemoText: (String) -> Unit,
    onClickAddTodoItemButton: (TodoData) -> Unit,
    onClickDeleteAllTodoItemsButton: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = titleText, onValueChange = onValueChangeTitleText, label = {
            Text(text = "Input Here Todo Title.")
        }, modifier = Modifier.testTag(stringResource(id = R.string.tag_field_title)))
        TextField(value = memoText, onValueChange = onValueChangeMemoText, label = {
            Text(text = "Input Here Todo Memo.")
        }, modifier = Modifier.testTag(stringResource(id = R.string.tag_field_memo)))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedButton(onClick = {
                val todoData =
                    TodoData(title = titleText, memo = memoText, registrationDate = Date())
                onClickAddTodoItemButton(todoData)
            }, enabled = enabledAddButton) {
                Text(text = stringResource(id = R.string.add_todo_item))
            }

            FilledTonalButton(
                onClick = {
                    onClickDeleteAllTodoItemsButton()
                }
            ) {
                Text(text = stringResource(id = R.string.delete_todo_items))
            }
        }
    }
}

@Composable
internal fun TodoListContent(
    uiState: FavoriteUiState,
    onClickTodoItem: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        when (uiState) {
            FavoriteUiState.Initial -> {

            }
            is FavoriteUiState.Error -> {
                Text(text = uiState.errorMessage)
            }
            is FavoriteUiState.UpdateTodoList -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .testTag(stringResource(id = R.string.tag_todo_item_list))
                ) {
                    itemsIndexed(uiState.todoList) { _, todoData ->
                        TodoItem(
                            todoData = todoData,
                            onClick = {
                                onClickTodoItem(todoData.id)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
fun AddTodoItemContent_Preview_Button_Disable() {
    TemplateApp01Theme {
        AddTodoItemContent(
            titleText = "",
            memoText = "",
            enabledAddButton = false,
            onValueChangeTitleText = {},
            onValueChangeMemoText = {},
            onClickAddTodoItemButton = {},
            onClickDeleteAllTodoItemsButton = {}
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
fun AddTodoItemContent_Preview_Button_Enable() {
    TemplateApp01Theme {
        AddTodoItemContent(
            titleText = "",
            memoText = "",
            enabledAddButton = true,
            onValueChangeTitleText = {},
            onValueChangeMemoText = {},
            onClickAddTodoItemButton = {},
            onClickDeleteAllTodoItemsButton = {}
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
fun TodoListContent_Preview_UpdateTodoList() {
    val todoList = buildList {
        repeat(3) {
            add(
                TodoData(
                    title = "title :$it",
                    memo = "memo: $it",
                    completionDate = Date(),
                    registrationDate = Date()
                )
            )
        }
    }
    TemplateApp01Theme {
        TodoListContent(
            uiState = FavoriteUiState.UpdateTodoList(todoList),
            onClickTodoItem = {},
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
fun TodoListContent_Preview_Error() {
    TemplateApp01Theme {
        TodoListContent(
            uiState = FavoriteUiState.Error(errorMessage = "error!"),
            onClickTodoItem = {},
        )
    }
}