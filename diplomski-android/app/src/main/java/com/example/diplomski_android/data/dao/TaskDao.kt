package com.example.diplomski_android.data.dao

import androidx.room.*
import com.example.diplomski_android.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("select * from tasks")
    fun getAllFlow(): Flow<List<Task>>

    @Query("select * from tasks")
    fun getAll(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("select * from tasks where lesson_id=:id")
    fun getByLessonFlow(id: Long): Flow<List<Task>>

    @Query("select * from tasks where lesson_id=:id")
    fun getByLesson(id: Long): List<Task>

    @Delete
    suspend fun delete(task: Task)

    @Query("delete from tasks where lesson_id=:lessonId")
    suspend fun deleteByLesson(lessonId: Long)
}