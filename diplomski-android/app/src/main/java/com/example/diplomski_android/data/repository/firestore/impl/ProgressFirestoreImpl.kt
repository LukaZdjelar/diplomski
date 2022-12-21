package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.ProgressFirestore
import com.example.diplomski_android.model.Progress
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProgressFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): ProgressFirestore {
    override fun insert(progress: Progress) {
        firebaseFirestore.collection("progress").add(progress)
    }

    override suspend fun getAll(): List<Progress> {
        val task = firebaseFirestore.collection("progress").get().await()
        return task.toObjects(Progress::class.java)
    }
}