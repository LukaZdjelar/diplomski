package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.diplomski_android.model.Chapter

@Dao
interface ChapterDao {
    @Query("select * from chapters")
    fun getAll(): List<Chapter>
}