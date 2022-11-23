package com.example.diplomski_android

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.diplomski_android.databinding.ActivityMainBinding
import com.example.diplomski_android.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var navController: NavController
    private var activityMainBinding: ActivityMainBinding? = null
    var loggedUserId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding?.apply {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
            mainActivity = this@MainActivity
        }
        setContentView(activityMainBinding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        loggedUserId = sharedPref.getLong("user", 0L)

        if (loggedUserId != 0L){
            mainViewModel.setUserById(loggedUserId)
            navController.navigate(R.id.action_loginFragment_to_coursesFragment)
        }
        setupDrawer()

        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination.id == R.id.coursesFragment || destination.id == R.id.chaptersFragment || destination.id == R.id.lessonsFragment){
                sharedToolbar.visibility = View.VISIBLE
            }else{
                drawerLayoutMainActivity.closeDrawer(GravityCompat.START)
                sharedToolbar.visibility = View.GONE
            }
        }
    }

    private fun setupDrawer(){
        sharedToolbar.setNavigationOnClickListener {
            drawerLayoutMainActivity.openDrawer(GravityCompat.START)
        }
        tvSignOut.setOnClickListener {
            val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().remove("user").apply()
            mainViewModel.setUser(User())
            Navigation.findNavController(fragmentContainerView).navigate(getSignOutAction())
        }
        buttonUpdateProfile.setOnClickListener {
            mainViewModel.setNewUser(mainViewModel.user.value!!)
            Navigation.findNavController(fragmentContainerView).navigate(getUpdateProfileAction())
        }
    }

    private fun getSignOutAction(): Int{
        return when (navController.currentDestination?.id){
            R.id.coursesFragment -> R.id.action_coursesFragment_to_loginFragment
            R.id.chaptersFragment -> R.id.action_chaptersFragment_to_loginFragment
            R.id.lessonsFragment -> R.id.action_lessonsFragment_to_loginFragment
            else -> 0
        }
    }

    private fun getUpdateProfileAction(): Int{
        return when (navController.currentDestination?.id){
            R.id.coursesFragment -> R.id.action_coursesFragment_to_registrationFragment
            R.id.chaptersFragment -> R.id.action_chaptersFragment_to_registrationFragment
            R.id.lessonsFragment -> R.id.action_lessonsFragment_to_registrationFragment
            else -> 0
        }
    }
}