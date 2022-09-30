package com.example.diplomski_android.model

data class Chapter(
    val id: Long,
    val courseId: Long,
    val name: String,
    val isLocked: Boolean,
    val lessons: List<Lesson>,
    val level: Int
)