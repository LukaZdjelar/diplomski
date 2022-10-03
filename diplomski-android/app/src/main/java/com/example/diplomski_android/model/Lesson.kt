package com.example.diplomski_android.model

data class Lesson(
    val id: Long,
    val chapterId: Long,
    val lessonStatus: String,
    val lessonType: String,
    val tasks: List<Task>?
)