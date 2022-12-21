package com.example.diplomski_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.diplomski_android.data.repository.firestore.AuthFirestore
import com.example.diplomski_android.data.repository.room.UserRepository
import com.example.diplomski_android.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authFirestore: AuthFirestore,
    private val userRepository: UserRepository,
) : ViewModel() {

    suspend fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return authFirestore.signInWithEmailAndPassword(email, password)
    }

    fun getUserByEmail(email: String): User {
        return userRepository.getByEmail(email)
    }

    fun sharedPreferencesPutUserId(id: Long) {
        userRepository.sharedPreferencesPutUserId(id)
    }
}