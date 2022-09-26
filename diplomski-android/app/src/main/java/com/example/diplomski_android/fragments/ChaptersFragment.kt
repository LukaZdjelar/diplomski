package com.example.diplomski_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.diplomski_android.R

class ChaptersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chapters, container, false)

        view.findViewById<Button>(R.id.chapters_courses_button).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_chaptersFragment_to_coursesFragment) }
        view.findViewById<Button>(R.id.chapters_task_button).setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_chaptersFragment_to_taskFragment) }

        return view
    }
}