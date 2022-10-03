package com.example.diplomski_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.adapter.CoursesAdapter
import com.example.diplomski_android.databinding.FragmentCoursesBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_courses.*

class CoursesFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var coursesBinding : FragmentCoursesBinding? = null
    private lateinit var coursesAdapter : CoursesAdapter
    private var coursesTest : List<Course>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentCoursesBinding.inflate(inflater, container, false)
        coursesBinding = fragmentBinding

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

        //TODO test, izbrisati
        coursesTest = listOf(Course(1, null ,"Course 1", null,null))

        coursesAdapter.differ.submitList(coursesTest)
    }

    private fun setupRecyclerView(){
        coursesAdapter = CoursesAdapter(mainViewModel)
        rvCourses.apply {
            adapter = coursesAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
}