package com.example.diplomski_android.data.repository.firestore.impl

import com.example.diplomski_android.data.repository.firestore.LanguageFirestore
import com.example.diplomski_android.model.Language
import com.google.firebase.firestore.FirebaseFirestore

class LanguageFirestoreImpl(private val firebaseFirestore: FirebaseFirestore): LanguageFirestore {
    override fun insert(language: Language) {
        firebaseFirestore.collection("languages").add(language)
    }
}