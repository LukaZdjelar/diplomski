package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.CourseFirestore
import com.example.diplomski_android.model.Course
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CourseFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): CourseFirestore {

    private val collection = firebaseFirestore.collection("courses")

    override fun insert(course: Course) {
        collection.add(course)
    }

    override suspend fun delete(course: Course) {
        val query = collection.whereEqualTo("id", course.id).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }

    override suspend fun getAll(): List<Course> {
        val task = firebaseFirestore.collection("courses").get().await()
        return task.toObjects(Course::class.java)
    }
}