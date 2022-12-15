package com.example.diplomski_android.data.repository.firestore

interface AuthFirestore {
    suspend fun createUserWithEmailAndPassword(email: String, password: String)
    suspend fun signInWithEmailAndPassword(email: String, password: String)
    suspend fun signOut()
}