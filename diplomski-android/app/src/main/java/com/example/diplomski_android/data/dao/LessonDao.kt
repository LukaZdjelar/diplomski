package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.diplomski_android.model.Lesson

@Dao
interface LessonDao {
    @Query("select * from lessons")
    fun getAll(): List<Lesson>
}