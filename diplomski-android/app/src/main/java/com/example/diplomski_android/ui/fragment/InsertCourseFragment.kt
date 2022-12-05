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
import com.example.diplomski_android.databinding.FragmentInsertCourseBinding
import com.example.diplomski_android.model.Language
import kotlinx.coroutines.flow.collectLatest

class InsertCourseFragment: Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var binding: FragmentInsertCourseBinding
    var languages = listOf<Language>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertCourseBinding.inflate(inflater, container, false)

        mainViewModel.getLanguages()
        lifecycleScope.launchWhenCreated {
            mainViewModel.languagesStateFlow.collectLatest {
                languages = it
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
            insertCourseFragment = this@InsertCourseFragment
        }
    }

    fun setAdapters(){
        val localAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, languages)
        binding.actvLocal.setAdapter(localAdapter)

        val foreignAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, languages)
        binding.actvForeign.setAdapter(foreignAdapter)

        if (mainViewModel.newCourse.value?.id == null){
            binding.actvLocal.setText("",false)
            binding.actvForeign.setText("",false)
        }else{
            binding.actvLocal.setText(mainViewModel.newCourse.value?.local_language?.name,false)
            binding.actvForeign.setText(mainViewModel.newCourse.value?.foreign_language?.name,false)
        }

//      TODO: Ne radi poziv viewmodel funkcije iz layout-a: onItemSelected
        binding.actvLocal.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onLocalLanguageItemSelected(localAdapter, position)
            binding.menuInsertCourseLocal.helperText = validateLocal()
        }

        binding.actvForeign.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onForeignLanguageItemSelected(foreignAdapter, position)
            binding.menuInsertCourseForeign.helperText = validateForeign()
        }

        binding.buttonInsertCourseConfirm.setOnClickListener {
            //TODO: da li postoji ta kombinacija jezika
            if (validateOnConfirm()){
                mainViewModel.onInsertCourseButtonClick()
                activity?.onBackPressed()
            }
        }
    }

    fun textChangeListener(){
        //TODO: onFocusChange ili onTextChange
        binding.insertCourseName.doOnTextChanged { _, _, _, _ ->
            binding.insertCourseNameContainer.helperText = validateName()
        }
    }

    fun validateName(): String?{
        val name = binding.insertCourseName.text.toString()
        if (name == ""){
            return "Required"
        }
        return null
    }

    fun validateLocal(): String?{
        val local = binding.actvLocal.text.toString()
        if (local == ""){
            return "Required"
        }
        if (local == binding.actvForeign.text.toString()){
            binding.menuInsertCourseForeign.helperText = "Languages can't be same"
            return "Languages can't be same"
        }else{
            binding.menuInsertCourseLocal.helperText = null
            binding.menuInsertCourseForeign.helperText = null
        }
        return null
    }

    fun validateForeign(): String?{
        val foreign = binding.actvForeign.text.toString()
        if (foreign == ""){
            return "Required"
        }
        if (foreign == binding.actvLocal.text.toString()){
            binding.menuInsertCourseLocal.helperText = "Languages can't be same"
            return "Languages can't be same"
        }else{
            binding.menuInsertCourseLocal.helperText = null
            binding.menuInsertCourseForeign.helperText = null
        }
        return null
    }

    fun validateOnConfirm(): Boolean{
        binding.insertCourseNameContainer.helperText = validateName()
        binding.menuInsertCourseLocal.helperText = validateLocal()
        binding.menuInsertCourseForeign.helperText = validateForeign()

        if (binding.insertCourseNameContainer.helperText == null &&
            binding.menuInsertCourseLocal.helperText == null &&
            binding.menuInsertCourseForeign.helperText == null) {
            return true
        }
        return false
    }
}
