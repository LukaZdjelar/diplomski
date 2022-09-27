package com.example.diplomski_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    private val _courseId = MutableLiveData<String>("")
    val courseId : LiveData<String> = _courseId

    fun setCourseId(selectedCourse: String){
        _courseId.value = selectedCourse
    }
}