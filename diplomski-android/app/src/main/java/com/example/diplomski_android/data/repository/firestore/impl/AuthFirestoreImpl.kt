package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.AuthFirestore
import com.example.diplomski_android.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthFirestoreImpl(private val firebase: Firebase): AuthFirestore {

    override suspend fun createUserWithEmailAndPassword(email: String, password: String) {
        firebase.auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String) {
        firebase.auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signOut() {
        firebase.auth.signOut()
    }
}