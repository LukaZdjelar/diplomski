package com.example.diplomski_android.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentCoursesBinding
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.ui.adapter.CoursesAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.layout_navigation_view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.flow.collectLatest

class CoursesFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var coursesBinding : FragmentCoursesBinding? = null
    private lateinit var coursesAdapter : CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCoursesBinding.inflate(inflater, container, false)
        coursesBinding = fragmentBinding

        mainViewModel.getCourses()
        mainViewModel.getLanguages()
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDrawer()

        coursesBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            coursesFragment = this@CoursesFragment
        }

        setupRecyclerView()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest {
                coursesAdapter.differ.submitList(it)
            }
        }

        button_create_course.setOnClickListener {
            mainViewModel.setNewCourse(Course())
            Navigation.findNavController(view).navigate(R.id.action_coursesFragment_to_insertCourseFragment)
        }
    }

    private fun setupDrawer(){
        sharedToolbar.setNavigationOnClickListener {
            coursesDrawerLayout.openDrawer(GravityCompat.START)
        }
        tvSignOut.setOnClickListener {
            val sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().remove("user").apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_coursesFragment_to_loginFragment)
        }
    }

    private fun setupRecyclerView(){
        coursesAdapter = CoursesAdapter(mainViewModel)
        rvCourses.apply {
            adapter = coursesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}