package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Progress

interface ProgressFirestore {
    fun insert(progress: Progress)
    suspend fun getAll(): List<Progress>
}