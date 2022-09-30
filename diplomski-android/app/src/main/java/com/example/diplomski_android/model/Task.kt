package com.example.diplomski_android.model

data class Task(
    val id: Long,
    val lessonId: Long,
    val question: String,
    val answer: String
)