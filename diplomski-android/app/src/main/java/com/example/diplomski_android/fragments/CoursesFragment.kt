package com.example.diplomski_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.R
import com.example.diplomski_android.adapter.CoursesAdapter
import com.example.diplomski_android.databinding.FragmentCoursesBinding
import com.example.diplomski_android.model.Course
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
        coursesTest = listOf(Course(1,null,"Course 1", null,null),
                                            Course(2,null,"Course 2", null, null),
                                            Course(3,null,"Course 3", null, null),
                                            Course(4,null,"Course 4", null, null),
                                            Course(5,null,"Course 5", null, null))

        coursesAdapter.differ.submitList(coursesTest)
    }

    fun onCourseSelection(text: String){
        mainViewModel.setCourseId(text)
        findNavController().navigate(R.id.action_coursesFragment_to_chaptersFragment)
    }

    private fun setupRecyclerView(){
        coursesAdapter = CoursesAdapter()
        rvCourses.apply {
            adapter = coursesAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
}