package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentInsertChapterBinding
import com.example.diplomski_android.model.Course
import kotlinx.coroutines.flow.collectLatest

class InsertChapterFragment : Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInsertChapterBinding
    var courses = listOf<Course>()
    val difficultyList = listOf("Basic","Intermediate","Advanced")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertChapterBinding.inflate(inflater, container, false)

        mainViewModel.getCourses()
        lifecycleScope.launchWhenCreated {
            mainViewModel.coursesStateFlow.collectLatest {
                courses = it
            }
        }

        textChangeListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertChapterFragment = this@InsertChapterFragment
        }
    }

    fun setAdapters(){
        val courseAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, courses)
        binding.actvChapterCourse.setAdapter(courseAdapter)

        val difficultyAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, difficultyList)
        binding.actvChapterDifficulty.setAdapter(difficultyAdapter)

        if (mainViewModel.newChapter.value?.id == null){
            binding.actvChapterCourse.setText(mainViewModel.newChapter.value?.course!!.name,false)
            mainViewModel.newChapter.value?.course_id = mainViewModel.newChapter.value?.course?.id
            binding.actvChapterDifficulty.setText("",false)
        }else{
            binding.actvChapterCourse.setText(mainViewModel.newChapter.value?.course!!.name,false)
            binding.actvChapterDifficulty.setText(mainViewModel.newChapter.value?.difficulty!!,false)
        }

        binding.actvChapterCourse.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onChapterCourseItemSelected(courseAdapter, position)
            binding.menuInsertChapterCourses.helperText = validateCourse()
        }

        binding.actvChapterDifficulty.setOnItemClickListener{ _, _, position, _ ->
            mainViewModel.onChapterDifficultyItemSelected(difficultyAdapter, position)
            binding.menuInsertChapterDifficulty.helperText = validateDifficulty()
        }

        binding.buttonInsertChapterConfirm.setOnClickListener {
            if (validateOnConfirm()){
                mainViewModel.onInsertChapterButtonClick()
                activity?.onBackPressed()
            }
        }
    }

    fun textChangeListener(){
        binding.insertChapterName.doOnTextChanged { _, _, _, _ ->
            binding.insertChapterNameContainer.helperText = validateName()
        }
    }

    fun validateCourse(): String?{
        val course = binding.actvChapterCourse.text.toString()
        if (course == ""){
            return "Required"
        }
        return null
    }

    fun validateName(): String?{
        val name = binding.insertChapterName.text.toString()
        if (name == ""){
            return "Required"
        }
        return null
    }

    fun validateDifficulty(): String?{
        val difficulty = binding.actvChapterDifficulty.text.toString()
        if (difficulty == ""){
            return "Required"
        }
        return null
    }

    fun validateOnConfirm(): Boolean{
        binding.menuInsertChapterCourses.helperText = validateCourse()
        binding.insertChapterNameContainer.helperText = validateName()
        binding.menuInsertChapterDifficulty.helperText = validateDifficulty()

        if (binding.menuInsertChapterCourses.helperText == null &&
            binding.insertChapterNameContainer.helperText == null &&
            binding.menuInsertChapterDifficulty.helperText == null){
            return true
        }
        return false
    }
}