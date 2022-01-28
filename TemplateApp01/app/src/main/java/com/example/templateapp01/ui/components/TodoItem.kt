package com.example.templateapp01.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.templateapp01.domain.model.TodoData
import com.example.templateapp01.ui.theme.TemplateApp01Theme
import java.util.*

@Composable
internal fun TodoItem(
    modifier: Modifier = Modifier,
    todoData: TodoData,
    onClick: () -> Unit,
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
                .clickable(onClick = { onClick() })
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            if (todoData.completionDate != null) {
                Icon(
                    Icons.Filled.Done,
                    contentDescription = "Completed Todo Item."
                )
            }
            Text(
                text = "Title: " + todoData.title,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Memo: " + todoData.memo,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
fun TodoItem_Preview_Completed() {
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PIXEL_4)
@Composable
fun TodoItem_Preview_InComplete() {
    TemplateApp01Theme {
        TodoItem(
            todoData = TodoData(
                id = 1,
                title = "title!",
                memo = "memo!",
                completionDate = null,
                registrationDate = Date()
            ), onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}