package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.ProgressFirestore
import com.example.diplomski_android.model.Progress
import com.google.firebase.firestore.FirebaseFirestore

class ProgressFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): ProgressFirestore {
    override fun insert(progress: Progress) {
        firebaseFirestore.collection("progress").add(progress)
    }
}