package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_courses.*
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

    private fun setupRecyclerView(){
        coursesAdapter = CoursesAdapter(mainViewModel)
        rvCourses.apply {
            adapter = coursesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}