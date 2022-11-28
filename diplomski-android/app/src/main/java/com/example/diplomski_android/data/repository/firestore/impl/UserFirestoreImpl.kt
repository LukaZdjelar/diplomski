package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.UserFirestore
import com.example.diplomski_android.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): UserFirestore {
    override fun insert(user: User) {
        firebaseFirestore.collection("users").add(user)
    }
}