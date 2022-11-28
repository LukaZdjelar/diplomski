package com.example.diplomski_android.data.repository.room.impl

import com.example.diplomski_android.data.dao.ChapterDao
import com.example.diplomski_android.data.repository.room.ChapterRepository
import com.example.diplomski_android.model.Chapter
import kotlinx.coroutines.flow.Flow

class ChapterRepositoryImpl(private val chapterDao: ChapterDao): ChapterRepository {

    override fun getAllFlow(): Flow<List<Chapter>> {
        return chapterDao.getAllFlow()
    }

    override fun getAll(): List<Chapter> {
        return chapterDao.getAll()
    }

    override fun getByCourseFlow(id: Long): Flow<List<Chapter>> {
        return chapterDao.getByCourseFlow(id)
    }

    override fun getByCourse(id: Long): List<Chapter> {
        return chapterDao.getByCourse(id)
    }

    override fun getById(id: Long): Chapter {
        return chapterDao.getById(id)
    }

    override suspend fun insert(chapter: Chapter): Long {
        return chapterDao.insert(chapter)
    }

    override suspend fun insertAll(chapters: List<Chapter>) {
        chapterDao.insertAll(chapters)
    }

    override suspend fun delete(chapter: Chapter) {
        return chapterDao.delete(chapter)
    }

    override suspend fun deleteAll() {
        chapterDao.deleteAll()
    }

    override suspend fun deleteByCourse(courseId: Long) {
        return chapterDao.deleteByCourse(courseId)
    }

    override fun countTotalLessons(chapterId: Long): Int {
        return chapterDao.countTotalLessons(chapterId)
    }

    override fun countCompletedLessons(chapterId: Long, userId: Long): Int {
        return chapterDao.countCompletedLessons(chapterId, userId)
    }
}