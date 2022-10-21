package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Course

interface CourseRepository {
    fun getAll(): List<Course>
    suspend fun insert(course: Course)
}