package com.example.diplomski_android.data.repository.firestore.impl

import android.util.Log
import com.example.diplomski_android.data.repository.firestore.UserFirestore
import com.example.diplomski_android.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): UserFirestore {
    override fun insert(user: User) {
        Firebase.auth.createUserWithEmailAndPassword(user.email!!, user.password!!).addOnCompleteListener { task ->
            if (task.isSuccessful){
                firebaseFirestore.collection("users").add(user)
            }
        }
    }
}