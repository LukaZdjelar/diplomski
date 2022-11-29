package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.LessonFirebase
import com.example.diplomski_android.model.Lesson
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LessonFirebaseImpl(private val firebaseFirestore: FirebaseFirestore): LessonFirebase {

    private val collection = firebaseFirestore.collection("lessons")

    override fun insert(lesson: Lesson) {
        collection.add(lesson)
    }

    override suspend fun delete(lesson: Lesson) {
        val query = collection.whereEqualTo("id", lesson.id).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }

    override suspend fun deleteByChapter(chapterId: Long) {
        val query = collection.whereEqualTo("chapter_id", chapterId).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }
}