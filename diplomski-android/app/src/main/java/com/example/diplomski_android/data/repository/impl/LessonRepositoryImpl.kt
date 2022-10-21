package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.LessonDao
import com.example.diplomski_android.data.repository.LessonRepository
import com.example.diplomski_android.model.Lesson

class LessonRepositoryImpl(private val lessonDao: LessonDao): LessonRepository {

    override fun getAll(): List<Lesson> {
        return lessonDao.getAll()
    }
}