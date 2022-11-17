package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.User

interface UserRepository {
    suspend fun insert(user: User)
    fun getById(id: Long): User
    fun getByEmail(email: String): User
}