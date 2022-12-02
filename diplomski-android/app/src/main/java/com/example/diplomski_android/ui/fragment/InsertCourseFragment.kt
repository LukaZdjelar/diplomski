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

//      set initial text
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
        }

        binding.actvForeign.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onForeignLanguageItemSelected(foreignAdapter, position)
        }

        binding.buttonInsertCourseConfirm.setOnClickListener {
            mainViewModel.onInsertCourseButtonClick()
            activity?.onBackPressed()
        }
    }
}
