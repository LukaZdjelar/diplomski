package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Chapter
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("select * from chapters")
    fun getAll(): Flow<List<Chapter>>
    @Query("select * from chapters where course_id=:id")
    fun getByCourse(id: Long): Flow<List<Chapter>>
    @Query("select * from chapters where id=:id")
    fun getById(id: Long): Chapter
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: Chapter)
}