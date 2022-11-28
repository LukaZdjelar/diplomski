package com.example.diplomski_android.data.repository.room.impl

import com.example.diplomski_android.data.dao.LessonDao
import com.example.diplomski_android.data.repository.room.LessonRepository
import com.example.diplomski_android.model.Lesson
import kotlinx.coroutines.flow.Flow

class LessonRepositoryImpl(private val lessonDao: LessonDao): LessonRepository {

    override fun getAllFlow(): Flow<List<Lesson>> {
        return lessonDao.getAllFlow()
    }

    override fun getAll(): List<Lesson> {
        return lessonDao.getAll()
    }

    override fun getByChapterFlow(id: Long): Flow<List<Lesson>> {
        return lessonDao.getByChapterFlow(id)
    }

    override fun getByChapter(id: Long): List<Lesson> {
        return lessonDao.getByChapter(id)
    }

    override fun getById(id: Long): Lesson {
        return lessonDao.getById(id)
    }

    override suspend fun insert(lesson: Lesson): Long {
        return lessonDao.insert(lesson)
    }

    override suspend fun insertAll(lessons: List<Lesson>) {
        return lessonDao.insertAll(lessons)
    }

    override suspend fun delete(lesson: Lesson) {
        return lessonDao.delete(lesson)
    }

    override suspend fun deleteAll() {
        return lessonDao.deleteAll()
    }

    override suspend fun deleteByChapter(chapterId: Long) {
        return lessonDao.deleteByChapter(chapterId)
    }

    override fun getLessonStatus(lessonId: Long, userId: Long): Boolean {
        return lessonDao.getLessonStatus(lessonId, userId)
    }
}