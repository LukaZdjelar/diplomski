package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.LanguageFirestore
import com.example.diplomski_android.model.Language
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LanguageFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): LanguageFirestore {
    override fun insert(language: Language) {
        firebaseFirestore.collection("languages").add(language)
    }

    override suspend fun getAll(): List<Language> {
        val task = firebaseFirestore.collection("languages").get().await()
        return task.toObjects(Language::class.java)
    }
}