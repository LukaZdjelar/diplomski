package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentInsertChapterBinding
import com.example.diplomski_android.model.Course
import kotlinx.android.synthetic.main.fragment_insert_chapter.*
import kotlinx.coroutines.flow.collectLatest

class InsertChapterFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var insertChapterBinding: FragmentInsertChapterBinding? = null
    var courses = listOf<Course>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentInsertChapterBinding.inflate(inflater, container, false)
        insertChapterBinding = fragmentBinding

        mainViewModel.getCourses()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest {
                courses = it
            }
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()

        insertChapterBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertChapterFragment = this@InsertChapterFragment
        }
    }

    fun setAdapters(){
        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        actv_chapter_course.setAdapter(courseAdapter)

        if (mainViewModel.newChapter.value?.id == null){
            actv_chapter_course.setText("",false)
        }else{
            actv_chapter_course.setText(mainViewModel.newChapter.value?.course!!.name,false)
        }

        actv_chapter_course.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onChapterCourseItemSelected(courseAdapter, position)
        }
    }
}