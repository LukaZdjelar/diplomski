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
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentCoursesBinding
import com.example.diplomski_android.viewmodel.MainViewModel

class CoursesFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var coursesBinding : FragmentCoursesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_courses, container, false)
//
//        view.findViewById<Button>(R.id.courses_chapters_button).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_coursesFragment_to_chaptersFragment) }
//
//        return view
        val fragmentBinding = FragmentCoursesBinding.inflate(inflater, container, false)
        coursesBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesBinding?.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner

            // Assign the view model to a property in the binding class
            viewModel = mainViewModel

            // Assign the fragment
            coursesFragment = this@CoursesFragment
        }
    }

    fun onCourseSelection(text: String){
        mainViewModel.setCourseId(text)
        findNavController().navigate(R.id.action_coursesFragment_to_chaptersFragment)
    }
}