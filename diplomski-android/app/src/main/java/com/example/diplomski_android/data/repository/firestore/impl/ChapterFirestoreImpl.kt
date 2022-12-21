package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.ChapterFirestore
import com.example.diplomski_android.model.Chapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ChapterFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): ChapterFirestore {

    private val collection = firebaseFirestore.collection("chapters")

    override fun insert(chapter: Chapter) {
        collection.add(chapter)
    }

    override suspend fun delete(chapter: Chapter) {
        val query = collection.whereEqualTo("id", chapter.id).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }

    override suspend fun deleteByCourse(courseId: Long) {
        val query = collection.whereEqualTo("course_id", courseId).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }

    override suspend fun getAll(): List<Chapter> {
        val task = firebaseFirestore.collection("chapters").get().await()
        return task.toObjects(Chapter::class.java)

    }
}