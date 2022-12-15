package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.User


interface UserFirestore {
    fun insert(user: User)
    suspend fun getAll(): List<User>
}