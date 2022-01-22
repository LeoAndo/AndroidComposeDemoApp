package com.example.templateapp01.model

import java.util.*

data class TodoData(
    val id: Int = 0,
    val title: String,
    val memo: String,
    val completionDate: Date?,
    val registrationDate: Date,
)