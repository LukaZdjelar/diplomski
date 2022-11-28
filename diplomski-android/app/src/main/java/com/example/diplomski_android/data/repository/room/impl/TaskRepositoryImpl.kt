package com.example.diplomski_android.data.repository.room.impl

import com.example.diplomski_android.data.dao.TaskDao
import com.example.diplomski_android.data.repository.room.TaskRepository
import com.example.diplomski_android.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDao): TaskRepository {

    override fun getAllFlow(): Flow<List<Task>> {
        return taskDao.getAllFlow()
    }
    override fun getAll(): List<Task> {
        return taskDao.getAll()
    }
    override suspend fun insert(task: Task): Long{
        return taskDao.insert(task)
    }

    override suspend fun insertAll(tasks: List<Task>) {
        taskDao.insertAll(tasks)
    }

    override fun getByLessonFlow(id: Long): Flow<List<Task>> {
        return taskDao.getByLessonFlow(id)
    }

    override fun getByLesson(id: Long): List<Task> {
        return taskDao.getByLesson(id)
    }

    override suspend fun delete(task: Task) {
        return taskDao.delete(task)
    }

    override suspend fun deleteAll() {
        taskDao.deleteAll()
    }

    override suspend fun deleteByLesson(lessonId: Long) {
        return taskDao.deleteByLesson(lessonId)
    }

    override fun countByLesson(lessonId: Long): Int {
        return taskDao.countByLesson(lessonId)
    }
}