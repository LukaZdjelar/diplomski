package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentCreateLessonBinding
import kotlinx.android.synthetic.main.fragment_create_lesson.*

class CreateLessonFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createLessonBinding: FragmentCreateLessonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateLessonBinding.inflate(inflater, container, false)
        createLessonBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createLessonBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createLessonFragment = this@CreateLessonFragment
        }

        //TODO: Privremeno
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, items)
        (menu_lesson_courses.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (menu_lesson_chapters.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (menu_lesson_type.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}