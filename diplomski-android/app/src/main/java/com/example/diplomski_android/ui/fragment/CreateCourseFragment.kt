package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.util.Log
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
import com.example.diplomski_android.model.Course
import com.example.diplomski_android.model.Language
import kotlinx.android.synthetic.main.fragment_create_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCourseFragment: Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createCourseBinding: FragmentCreateCourseBinding? = null
    var languages = listOf<Language>()
    var createCourse = Course()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateCourseBinding.inflate(inflater, container, false)
        createCourseBinding = fragmentBinding

        CoroutineScope(Dispatchers.IO).launch {
            languages = mainViewModel.getLanguages()
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createCourseBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createCourseFragment = this@CreateCourseFragment
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, languages)

        //TODO: ???
        autv_native.setAdapter(adapter)
        autv_native.setOnItemClickListener { _, _, position, _ ->
            val localLanguage = adapter.getItem(position)!!
            createCourse.local_language_id = localLanguage.id
        }
        autv_foreign.setAdapter(adapter)
        autv_foreign.setOnItemClickListener { _, _, position, _ ->
            val foreignLanguage = adapter.getItem(position)!!
            createCourse.foreign_language_id = foreignLanguage.id
        }

        button_create_course_confirm.setOnClickListener {
            createCourse.name = ti_create_course_name.editText?.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.insertCourse(createCourse)
            }
        }
    }
}
