package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("select * from lessons")
    fun getAll(): Flow<List<Lesson>>
    @Query("select * from lessons where chapter_id=:id")
    fun getByChapter(id: Long): Flow<List<Lesson>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lesson: Lesson)
}