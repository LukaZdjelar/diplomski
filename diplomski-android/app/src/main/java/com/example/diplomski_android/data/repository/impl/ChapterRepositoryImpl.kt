package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.ChapterDao
import com.example.diplomski_android.data.repository.ChapterRepository
import com.example.diplomski_android.model.Chapter

class ChapterRepositoryImpl(private val chapterDao: ChapterDao): ChapterRepository {

    override fun getAll(): List<Chapter> {
        return chapterDao.getAll()
    }

    override fun getByCourse(id: Long): List<Chapter> {
        return chapterDao.getByCourse(id)
    }
}