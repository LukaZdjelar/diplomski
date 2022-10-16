package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentAdministratorPanelBinding
import com.example.diplomski_android.ui.viewmodel.MainViewModel
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
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_createCourseFragment)
        }
        button_create_chapter.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_createChapterFragment)
        }
        button_create_lesson.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_administratorPanelFragment_to_createLessonFragment)
        }
    }
}