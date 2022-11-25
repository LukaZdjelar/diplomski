package com.example.diplomski_android.data.dao

import androidx.room.*
import com.example.diplomski_android.model.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("select * from courses")
    fun getAllFlow(): Flow<List<Course>>

    @Query("select * from courses")
    fun getAll(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(courses: List<Course>)

    @Query("select * from courses where id=:id")
    fun getById(id: Long): Course

    @Delete
    suspend fun delete(course: Course)

    @Query("delete from courses")
    suspend fun deleteAll()
}