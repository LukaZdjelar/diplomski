package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.diplomski_android.model.Task

@Dao
interface TaskDao {

    @Query("select * from tasks")
    fun getAll(): List<Task>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)
    @Query("select * from tasks where lesson_id=:id")
    fun getByLesson(id: Long): List<Task>
}