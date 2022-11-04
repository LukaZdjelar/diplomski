package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentAdministratorPanelBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task
import kotlinx.android.synthetic.main.fragment_administrator_panel.*

class AdministratorPanelFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var administratorPanelBinding: FragmentAdministratorPanelBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentAdministratorPanelBinding.inflate(inflater, container, false)
        administratorPanelBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        administratorPanelBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            administratorPanelFragment = this@AdministratorPanelFragment
        }

        button_create_course.setOnClickListener {
            mainViewModel.setNewCourse(Course())
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_insertCourseFragment)
        }
        button_create_chapter.setOnClickListener {
            mainViewModel.setNewChapter(Chapter())
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_insertChapterFragment)
        }
        button_create_lesson.setOnClickListener {
            mainViewModel.setNewLesson(Lesson())
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_insertLessonFragment)
        }
        button_create_task.setOnClickListener {
            mainViewModel.setNewTask(Task())
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_insertTaskFragment)
        }
    }
}