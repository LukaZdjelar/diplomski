package com.example.diplomski_android.data.repository.impl

import com.example.diplomski_android.data.dao.UserDao
import com.example.diplomski_android.data.repository.UserRepository
import com.example.diplomski_android.model.User

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun insert(user: User) {
        userDao.insert(user)
    }
}