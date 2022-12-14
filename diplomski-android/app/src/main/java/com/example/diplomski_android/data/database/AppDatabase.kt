package com.example.diplomski_android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diplomski_android.data.dao.*
import com.example.diplomski_android.model.*

@Database(entities = [Course::class, Chapter::class, Lesson::class, Task::class, Language::class, User::class, Progress::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun chapterDao(): ChapterDao
    abstract fun lessonDao(): LessonDao
    abstract fun taskDao(): TaskDao
    abstract fun languageDao(): LanguageDao
    abstract fun userDao(): UserDao
    abstract fun progressDao(): ProgressDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            tempInstance?.let {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}