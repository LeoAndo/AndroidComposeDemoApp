package com.example.templateapp01.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapp01.model.TodoData
import com.example.templateapp01.ui.theme.TemplateApp01Theme
import java.util.*

@Composable
internal fun TodoItem(
    todoData: TodoData,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    MyAppSurface(
        border = BorderStroke(1.dp, Color.Green),
        shape = RoundedCornerShape(
            topStartPercent = 10,
            topEndPercent = 30,
            bottomStartPercent = 0,
            bottomEndPercent = 40
        ),
        color = Color.White,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .clickable(onClick = { onClick(todoData.id) })
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = todoData.title,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = todoData.memo,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTodoItem() {
    TemplateApp01Theme {
        TodoItem(
            todoData = TodoData(
                id = 1,
                title = "title!",
                memo = "memo!",
                completionDate = Date(),
                registrationDate = Date()
            ), onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}