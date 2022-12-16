package com.example.diplomski_android.data.repository.room.impl

import android.content.SharedPreferences
import com.example.diplomski_android.data.dao.UserDao
import com.example.diplomski_android.data.repository.room.UserRepository
import com.example.diplomski_android.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences
    ): UserRepository {

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

    override fun sharedPreferencesGetUserId(): Long {
        return sharedPreferences.getLong("user", 0L)
    }

    override fun sharedPreferencesPutUserId(id: Long) {
        sharedPreferences.edit().putLong("user", id).apply()
    }

    override fun sharedPreferencesRemoveUserId() {
        sharedPreferences.edit().remove("user").apply()
    }
}