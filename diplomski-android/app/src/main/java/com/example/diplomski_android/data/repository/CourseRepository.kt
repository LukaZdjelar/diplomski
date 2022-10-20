package com.example.diplomski_android.data.repository

import com.example.diplomski_android.data.dao.CourseDao
import com.example.diplomski_android.model.Course

class CourseRepository(private val courseDao: CourseDao) {

    fun getAll(): List<Course> {
        return courseDao.getAll()
    }
}