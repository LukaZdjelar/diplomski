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
import com.example.diplomski_android.databinding.FragmentCreateTaskBinding
import kotlinx.android.synthetic.main.fragment_create_task.*

class CreateTaskFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createTaskBinding: FragmentCreateTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateTaskBinding.inflate(inflater, container, false)
        createTaskBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createTaskBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createTaskFragment = this@CreateTaskFragment
        }

        //TODO: Privremeno
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (menu_task_lessons.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (menu_task_chapters.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (menu_task_courses.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}