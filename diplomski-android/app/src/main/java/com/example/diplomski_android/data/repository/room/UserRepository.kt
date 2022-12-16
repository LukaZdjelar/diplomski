package com.example.diplomski_android.data.repository.room

import com.example.diplomski_android.model.User

interface UserRepository {
    suspend fun insert(user: User): Long
    suspend fun insertAll(users: List<User>)
    fun getById(id: Long): User
    fun getByEmail(email: String): User
    fun getIsAdmin(id: Long): Boolean
    suspend fun deleteAll()
    fun sharedPreferencesGetUserId(): Long
    fun sharedPreferencesPutUserId(id: Long)
    fun sharedPreferencesRemoveUserId()
}