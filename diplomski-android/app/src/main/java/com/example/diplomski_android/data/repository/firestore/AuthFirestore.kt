package com.example.diplomski_android.data.repository.firestore

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthFirestore {
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>
    suspend fun signOut(): Unit
}