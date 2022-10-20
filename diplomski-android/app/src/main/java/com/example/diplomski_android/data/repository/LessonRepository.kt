package com.example.diplomski_android.data.repository

import com.example.diplomski_android.data.dao.LessonDao
import com.example.diplomski_android.model.Lesson

class LessonRepository(private val lessonDao: LessonDao) {

    fun getAll(): List<Lesson> {
        return lessonDao.getAll()
    }
}