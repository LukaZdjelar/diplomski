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
import com.example.diplomski_android.model.Language
import com.example.diplomski_android.ui.adapter.CoursesAdapter
import kotlinx.coroutines.flow.collectLatest

class CoursesFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding : FragmentCoursesBinding
    private lateinit var coursesAdapter : CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoursesBinding.inflate(inflater, container, false)

        mainViewModel.getCourses()
        mainViewModel.getLanguages()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
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

        binding.buttonCreateCourse.setOnClickListener {
            mainViewModel.setNewCourse(Course())
            Navigation.findNavController(view).navigate(R.id.action_coursesFragment_to_insertCourseFragment)
        }

        binding.buttonCreateLanguage.setOnClickListener {
            mainViewModel.setNewLanguage(Language())
            Navigation.findNavController(view).navigate(R.id.action_coursesFragment_to_insertLanguageFragment)
        }
    }

    private fun setupRecyclerView(){
        coursesAdapter = CoursesAdapter(mainViewModel)
        binding.rvCourses.apply {
            adapter = coursesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}