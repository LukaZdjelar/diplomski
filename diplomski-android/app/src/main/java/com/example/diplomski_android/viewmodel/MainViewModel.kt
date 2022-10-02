package com.example.diplomski_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomski_android.model.Course

class MainViewModel:ViewModel() {
    private val _course = MutableLiveData<Course>()
    val course : LiveData<Course> = _course

    fun setCourse(selectedCourse: Course){
        _course.value = selectedCourse
    }
}