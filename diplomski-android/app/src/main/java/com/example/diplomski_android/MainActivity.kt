package com.example.diplomski_android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
//        sharedPref.edit().clear().apply()
        val loggedUserId = sharedPref.getLong("user", 0L)


        if (loggedUserId != 0L){
            navController.navigate(R.id.action_loginFragment_to_coursesFragment)
        }
    }
}