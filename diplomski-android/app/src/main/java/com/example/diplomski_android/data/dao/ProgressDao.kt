package com.example.diplomski_android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.diplomski_android.model.Progress

@Dao
interface ProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: Progress)
}