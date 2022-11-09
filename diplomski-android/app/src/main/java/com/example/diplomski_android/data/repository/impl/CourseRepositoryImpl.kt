package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.CourseDao
import com.example.diplomski_android.data.repository.CourseRepository
import com.example.diplomski_android.model.Course
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDao: CourseDao): CourseRepository {

    override fun getAllFlow(): Flow<List<Course>> {
        return courseDao.getAllFlow()
    }
    override fun getAll(): List<Course> {
        return courseDao.getAll()
    }
    override suspend fun insert(course: Course){
        return courseDao.insert(course)
    }
    override fun getById(id: Long): Course {
        return courseDao.getById(id)
    }

    override suspend fun delete(course: Course) {
        return courseDao.delete(course)
    }
}