package com.example.diplomski_android.data.repository

import com.example.diplomski_android.model.Progress

interface ProgressRepository {

    suspend fun insert(progress: Progress)
}