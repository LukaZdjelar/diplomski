package com.example.diplomski_android.data.repository.firestore

import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course

interface ChapterFirestore {
    fun insert(chapter: Chapter)
}