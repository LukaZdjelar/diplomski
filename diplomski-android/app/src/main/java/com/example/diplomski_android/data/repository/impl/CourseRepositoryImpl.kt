package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.CourseDao
import com.example.diplomski_android.data.repository.CourseRepository
import com.example.diplomski_android.model.Course
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(private val courseDao: CourseDao): CourseRepository {

    override fun getAll(): List<Course> {
        return courseDao.getAll()
    }
    override suspend fun insert(course: Course){
        return courseDao.insert(course)
    }
}