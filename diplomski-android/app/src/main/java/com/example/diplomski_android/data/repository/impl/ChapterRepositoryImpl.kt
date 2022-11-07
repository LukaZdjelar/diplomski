package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.ChapterDao
import com.example.diplomski_android.data.repository.ChapterRepository
import com.example.diplomski_android.model.Chapter
import kotlinx.coroutines.flow.Flow

class ChapterRepositoryImpl(private val chapterDao: ChapterDao): ChapterRepository {

    override fun getAll(): Flow<List<Chapter>> {
        return chapterDao.getAll()
    }

    override fun getByCourse(id: Long): Flow<List<Chapter>> {
        return chapterDao.getByCourse(id)
    }

    override fun getById(id: Long): Chapter {
        return chapterDao.getById(id)
    }

    override suspend fun insert(chapter: Chapter) {
        return chapterDao.insert(chapter)
    }
}