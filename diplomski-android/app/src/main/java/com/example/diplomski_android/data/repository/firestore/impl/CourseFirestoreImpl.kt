package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.CourseFirestore
import com.example.diplomski_android.model.Course
import com.google.firebase.firestore.FirebaseFirestore

class CourseFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): CourseFirestore {

    override fun insert(course: Course) {
        firebaseFirestore.collection("courses").add(course)
    }
}