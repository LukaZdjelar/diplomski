package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Course

@Dao
interface CourseDao {
    @Query("select * from courses")
    fun getAll(): List<Course>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(course: Course)
}