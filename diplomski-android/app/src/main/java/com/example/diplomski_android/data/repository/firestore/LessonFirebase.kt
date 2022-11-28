package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Lesson

interface LessonFirebase {
    fun insert(lesson: Lesson)
}