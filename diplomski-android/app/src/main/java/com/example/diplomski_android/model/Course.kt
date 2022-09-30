package com.example.diplomski_android.model

data class Course(
    val id: Int,
    val chapters: List<Chapter>?,
    val name: String,
    val local: Language?,
    val foreign: Language?
)