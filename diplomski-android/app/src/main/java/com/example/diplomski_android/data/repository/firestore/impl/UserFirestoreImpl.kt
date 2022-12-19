package com.example.diplomski_android.data.repository.firestore.impl

import android.util.Log
import com.example.diplomski_android.data.repository.firestore.UserFirestore
import com.example.diplomski_android.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): UserFirestore {
    override fun insert(user: User) {
        user.email?.let { email ->
            user.password?.let { password ->
                Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        firebaseFirestore.collection("users").add(user)
                    }
                }
            }
        }
    }

    override suspend fun getAll(): List<User> {
        val loadUsers = firebaseFirestore.collection("users").get().await()
        return loadUsers.toObjects(User::class.java)
    }
}