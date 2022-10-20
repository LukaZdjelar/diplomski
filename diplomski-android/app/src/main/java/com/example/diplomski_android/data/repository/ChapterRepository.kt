package com.example.diplomski_android.data.repository

import com.example.diplomski_android.data.dao.ChapterDao
import com.example.diplomski_android.model.Chapter

class ChapterRepository(private val chapterDao: ChapterDao) {

    fun getAll(): List<Chapter> {
        return chapterDao.getAll()
    }
}