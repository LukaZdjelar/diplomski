package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.LessonDao
import com.example.diplomski_android.data.repository.LessonRepository
import com.example.diplomski_android.model.Lesson
import kotlinx.coroutines.flow.Flow

class LessonRepositoryImpl(private val lessonDao: LessonDao): LessonRepository {

    override fun getAll(): Flow<List<Lesson>> {
        return lessonDao.getAll()
    }

    override fun getByChapter(id: Long): Flow<List<Lesson>> {
        return lessonDao.getByChapter(id)
    }

    override suspend fun insert(lesson: Lesson) {
        return lessonDao.insert(lesson)
    }
}