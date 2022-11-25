package com.example.diplomski_android

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.diplomski_android.databinding.ActivityMainBinding
import com.example.diplomski_android.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var database: FirebaseFirestore
    private lateinit var navController: NavController
    private var activityMainBinding: ActivityMainBinding? = null
    var loggedUserId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //FIREBASE
        database = Firebase.firestore
        var users: List<User>
        var courses: List<Course>
        var chapters: List<Chapter>
        var lessons: List<Lesson>
        var tasks: List<Task>
        var languages: List<Language>
        var progress: List<Progress>

        runBlocking {
            val loadUsers = database.collection("users").get().await()
            users = loadUsers.toObjects(User::class.java)
            mainViewModel.usersFirebaseSync(users)
        }

        database.collection("courses").get().addOnCompleteListener {
            courses = it.result.toObjects(Course::class.java)
            lifecycleScope.launch {
                mainViewModel.coursesFirebaseSync(courses)
            }
        }

        database.collection("chapters").get().addOnCompleteListener {
            chapters = it.result.toObjects(Chapter::class.java)
            lifecycleScope.launch {
                mainViewModel.chaptersFirebaseSync(chapters)
            }
        }

        database.collection("lessons").get().addOnCompleteListener {
            lessons = it.result.toObjects(Lesson::class.java)
            lifecycleScope.launch {
                mainViewModel.lessonsFirebaseSync(lessons)
            }
        }

        database.collection("tasks").get().addOnCompleteListener {
            tasks = it.result.toObjects(Task::class.java)
            lifecycleScope.launch {
                mainViewModel.tasksFirebaseSync(tasks)
            }
        }

        database.collection("languages").get().addOnCompleteListener {
            languages = it.result.toObjects(Language::class.java)
            lifecycleScope.launch {
                mainViewModel.languagesFirebaseSync(languages)
            }
        }

        database.collection("progress").get().addOnCompleteListener {
            progress = it.result.toObjects(Progress::class.java)
            lifecycleScope.launch {
                mainViewModel.progressFirebaseSync(progress)
            }
        }

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