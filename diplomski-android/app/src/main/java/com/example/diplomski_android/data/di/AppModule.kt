package com.example.diplomski_android.data.di

import android.app.Application
import android.content.Context
import com.example.diplomski_android.MyApplication
import com.example.diplomski_android.data.dao.CourseDao
import com.example.diplomski_android.data.database.AppDatabase
import com.example.diplomski_android.data.repository.CourseRepository
import com.example.diplomski_android.data.repository.impl.CourseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return MyApplication.applicationContext()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase{
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCourseDao(appDatabase: AppDatabase): CourseDao{
        return appDatabase.courseDao()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(courseDao: CourseDao): CourseRepository{
        return CourseRepositoryImpl(courseDao)
    }
}