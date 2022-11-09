package com.example.diplomski_android.data.dao

import androidx.room.*
import com.example.diplomski_android.model.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("select * from chapters")
    fun getAllFlow(): Flow<List<Chapter>>

    @Query("select * from chapters")
    fun getAll(): List<Chapter>

    @Query("select * from chapters where course_id=:id")
    fun getByCourseFlow(id: Long): Flow<List<Chapter>>

    @Query("select * from chapters where course_id=:id")
    fun getByCourse(id: Long): List<Chapter>

    @Query("select * from chapters where id=:id")
    fun getById(id: Long): Chapter

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: Chapter)

    @Delete
    suspend fun delete(chapter: Chapter)
}