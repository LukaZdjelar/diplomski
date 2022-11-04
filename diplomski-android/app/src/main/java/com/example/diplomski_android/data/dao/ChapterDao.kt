package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Chapter

@Dao
interface ChapterDao {
    @Query("select * from chapters")
    fun getAll(): List<Chapter>
    @Query("select * from chapters where course_id=:id")
    fun getByCourse(id: Long): List<Chapter>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: Chapter)
}