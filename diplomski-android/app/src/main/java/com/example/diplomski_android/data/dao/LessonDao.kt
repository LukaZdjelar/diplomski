package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.diplomski_android.model.Lesson

@Dao
interface LessonDao {
    @Query("select * from lessons")
    fun getAll(): List<Lesson>
    @Query("select * from lessons where chapter_id=:id")
    fun getByChapter(id: Long): List<Lesson>
}