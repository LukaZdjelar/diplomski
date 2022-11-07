package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Lesson
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {
    @Query("select * from lessons")
    fun getAllFlow(): Flow<List<Lesson>>

    @Query("select * from lessons")
    fun getAll(): List<Lesson>

    @Query("select * from lessons where chapter_id=:id")
    fun getByChapterFlow(id: Long): Flow<List<Lesson>>

    @Query("select * from lessons where chapter_id=:id")
    fun getByChapter(id: Long): List<Lesson>

    @Query("select * from lessons where id=:id")
    fun getById(id: Long): Lesson

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lesson: Lesson)

    @Delete
    suspend fun delete(lesson: Lesson)
}