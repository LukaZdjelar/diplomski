package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.TaskDao
import com.example.diplomski_android.data.repository.TaskRepository
import com.example.diplomski_android.model.Task

class TaskRepositoryImpl(private val taskDao: TaskDao): TaskRepository {

    override fun getAll(): List<Task> {
        return taskDao.getAll()
    }
    override suspend fun insert(task: Task){
        return taskDao.insert(task)
    }
    override fun getByLesson(id: Long): List<Task> {
        return taskDao.getByLesson(id)
    }
}