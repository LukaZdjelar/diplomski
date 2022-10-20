package com.example.diplomski_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.diplomski_android.data.database.AppDatabase
import com.example.diplomski_android.data.repository.TaskRepository
import com.example.diplomski_android.model.Task
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskDao = AppDatabase.getDatabase(this).taskDao()
        lifecycleScope.launch {
            taskDao.insert(Task(1,1,"pitanje","odgovor"))
        }
    }
}