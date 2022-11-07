package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("select * from courses")
    fun getAll(): Flow<List<Course>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)
    @Query("select * from courses where id=:id")
    fun getById(id: Long): Course
}