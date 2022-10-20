package com.example.diplomski_android.data.repository

import com.example.diplomski_android.data.dao.TaskDao
import com.example.diplomski_android.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    fun getAll(): List<Task> {
        return taskDao.getAll()
    }
}