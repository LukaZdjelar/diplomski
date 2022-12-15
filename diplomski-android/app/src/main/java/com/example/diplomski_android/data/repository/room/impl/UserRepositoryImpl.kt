package com.example.diplomski_android.data.repository.room.impl

import android.content.Context
import com.example.diplomski_android.R
import com.example.diplomski_android.data.dao.UserDao
import com.example.diplomski_android.data.repository.room.UserRepository
import com.example.diplomski_android.model.User

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun insert(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun insertAll(users: List<User>) {
        userDao.insertAll(users)
    }

    override fun getById(id: Long): User {
        return userDao.getById(id)
    }

    override fun getByEmail(email: String): User {
        return userDao.getByEmail(email)
    }

    override fun getIsAdmin(id: Long): Boolean {
        return userDao.getIsAdmin(id)
    }

    override suspend fun deleteAll() {
        userDao.deleteAll()
    }
}