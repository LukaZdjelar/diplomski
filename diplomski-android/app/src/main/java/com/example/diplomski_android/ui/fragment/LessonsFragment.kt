package com.example.diplomski_android.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentLessonsBinding
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.ui.adapter.LessonsAdapter
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.fragment_lessons.*
import kotlinx.android.synthetic.main.layout_navigation_view.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.flow.collectLatest

class LessonsFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var lessonsBinding : FragmentLessonsBinding? = null
    private lateinit var lessonsAdapter: LessonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentLessonsBinding.inflate(inflater, container, false)
        lessonsBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDrawer()

        lessonsBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            lessonsFragment = this@LessonsFragment
        }

        button_create_lesson.setOnClickListener {
            val lesson = Lesson()
            lesson.course = mainViewModel.currentCourse.value
            lesson.chapter = mainViewModel.currentChapter.value
            mainViewModel.setNewLesson(lesson)
            Navigation.findNavController(view).navigate(R.id.action_lessonsFragment_to_insertLessonFragment)
        }

        setupRecyclerView()
    }

    private fun setupDrawer(){
        sharedToolbar.setNavigationOnClickListener {
            lessonDrawerFragment.openDrawer(GravityCompat.START)
        }
        tvSignOut.setOnClickListener {
            val sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)
            sharedPref.edit().remove("user").apply()
            Navigation.findNavController(requireView()).navigate(R.id.action_lessonsFragment_to_loginFragment)
        }
    }

    private fun setupRecyclerView(){
        lessonsAdapter = LessonsAdapter(mainViewModel)
        rvLessons.apply {
            adapter = lessonsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getLessonsByChapterFlow(mainViewModel.currentChapter.value?.id!!)
        lifecycleScope.launchWhenResumed {
            mainViewModel.lessonsStateFlow.collectLatest {
                lessonsAdapter.differ.submitList(it)
            }
        }
    }
}