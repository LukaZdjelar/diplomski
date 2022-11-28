package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Task

interface TaskFirestore {
    fun insert(task: Task)
}