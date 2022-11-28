package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.LessonFirebase
import com.example.diplomski_android.model.Lesson
import com.google.firebase.firestore.FirebaseFirestore

class LessonFirebaseImpl(private val firebaseFirestore: FirebaseFirestore): LessonFirebase {
    override fun insert(lesson: Lesson) {
        firebaseFirestore.collection("lessons").add(lesson)
    }
}