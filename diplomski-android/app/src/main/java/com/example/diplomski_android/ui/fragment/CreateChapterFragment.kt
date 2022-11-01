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
import com.example.diplomski_android.databinding.FragmentCreateChapterBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Course
import kotlinx.android.synthetic.main.fragment_create_chapter.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateChapterFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var createChapterBinding: FragmentCreateChapterBinding? = null
    var courses = listOf<Course>()
    var createChapter = Chapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentCreateChapterBinding.inflate(inflater, container, false)
        createChapterBinding = fragmentBinding

        CoroutineScope(Dispatchers.IO).launch {
            courses = mainViewModel.getCourses()
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChapterBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            createChapterFragment = this@CreateChapterFragment
        }

        //TODO: ???
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)

        actv_chapter_course.setAdapter(adapter)
        actv_chapter_course.setOnItemClickListener { _, _, position, _ ->
            val course = adapter.getItem(position)
            createChapter.course_id = course!!.id
        }

        button_create_chapter_confirm.setOnClickListener {
            createChapter.name = ti_create_chapter_name.editText?.text.toString()
            createChapter.level = ti_create_chapter_level.editText?.text.toString().toInt()

            CoroutineScope(Dispatchers.IO).launch {
                mainViewModel.insertChapter(createChapter)
            }
        }
    }
}