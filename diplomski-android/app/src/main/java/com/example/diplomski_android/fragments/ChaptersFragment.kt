package com.example.diplomski_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentChaptersBinding
import com.example.diplomski_android.databinding.FragmentCoursesBinding
import com.example.diplomski_android.viewmodel.MainViewModel

class ChaptersFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var chaptersBinding : FragmentChaptersBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_chapters, container, false)
//
//        view.findViewById<Button>(R.id.chapters_courses_button).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_chaptersFragment_to_coursesFragment) }
//        view.findViewById<Button>(R.id.chapters_task_button).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_chaptersFragment_to_taskFragment) }
//
//        return view
        val fragmentBinding = FragmentChaptersBinding.inflate(inflater, container, false)
        chaptersBinding = fragmentBinding

        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chaptersBinding?.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner

            // Assign the view model to a property in the binding class
            viewModel = mainViewModel

            // Assign the fragment
            chaptersFragment = this@ChaptersFragment
        }
    }
}