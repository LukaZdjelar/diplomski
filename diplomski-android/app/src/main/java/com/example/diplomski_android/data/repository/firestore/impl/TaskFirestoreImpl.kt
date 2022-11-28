package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.TaskFirestore
import com.example.diplomski_android.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class TaskFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): TaskFirestore {
    override fun insert(task: Task) {
        firebaseFirestore.collection("tasks").add(task)
    }
}