package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Course

interface CourseFirestore {
    fun insert(course: Course)
    suspend fun delete(course: Course)
    suspend fun getAll(): List<Course>
}