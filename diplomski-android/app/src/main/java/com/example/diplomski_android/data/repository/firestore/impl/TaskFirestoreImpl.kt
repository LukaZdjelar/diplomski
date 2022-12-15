package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.TaskFirestore
import com.example.diplomski_android.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TaskFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): TaskFirestore {

    private val collection = firebaseFirestore.collection("tasks")

    override fun insert(task: Task) {
        collection.add(task)
    }

    override suspend fun delete(task: Task) {
        val query = collection.whereEqualTo("id", task.id).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }

    override suspend fun deleteByLesson(lessonId: Long) {
        val query = collection.whereEqualTo("lesson_id", lessonId).get().await()
        if (query.documents.isNotEmpty()){
            query.forEach { document ->
                collection.document(document.id).delete().await()
            }
        }
    }

    override suspend fun getAll(): List<Task> {
        val task = firebaseFirestore.collection("tasks").get().await()
        return task.toObjects(Task::class.java)
    }
}