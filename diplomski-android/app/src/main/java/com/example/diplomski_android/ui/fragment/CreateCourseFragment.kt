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
import com.example.diplomski_android.databinding.FragmentCreateCourseBinding
import kotlinx.android.synthetic.main.fragment_create_course.*

class CreateCourseFragment: Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createCourseBinding: FragmentCreateCourseBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateCourseBinding.inflate(inflater, container, false)
        createCourseBinding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createCourseBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createCourseFragment = this@CreateCourseFragment
        }

        //TODO: Privremeno
        val items = listOf("Language 1", "Language 2", "Language 3", "Language 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (menu_course_native.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        (menu_course_foreign.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}