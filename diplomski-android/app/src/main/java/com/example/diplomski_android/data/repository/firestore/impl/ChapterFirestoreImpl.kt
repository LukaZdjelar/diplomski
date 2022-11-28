package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.ChapterFirestore
import com.example.diplomski_android.model.Chapter
import com.google.firebase.firestore.FirebaseFirestore

class ChapterFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): ChapterFirestore {
    override fun insert(chapter: Chapter) {
        firebaseFirestore.collection("chapters").add(chapter)
    }
}