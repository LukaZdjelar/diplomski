package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Lesson

interface LessonRepository {
    fun getAll(): List<Lesson>
    fun getByChapter(id: Long): List<Lesson>
}