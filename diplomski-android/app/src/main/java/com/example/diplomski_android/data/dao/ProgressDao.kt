package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Progress

@Dao
interface ProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: Progress)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(progress: List<Progress>)

    @Query("select * from progress where user_id=:userId and lesson_id=:lessonId")
    fun findByUserAndLessonBoolean(userId: Long, lessonId: Long): Boolean

    @Query("delete from progress")
    suspend fun deleteAll()
}