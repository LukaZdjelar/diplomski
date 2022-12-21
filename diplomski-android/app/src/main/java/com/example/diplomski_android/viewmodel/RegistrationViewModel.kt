package com.example.diplomski_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomski_android.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(

) : ViewModel() {

    private val _newUser = MutableLiveData(User())
    val newUser: LiveData<User> = _newUser
    fun setNewUser(nu: User) {
        _newUser.value = nu
    }
}