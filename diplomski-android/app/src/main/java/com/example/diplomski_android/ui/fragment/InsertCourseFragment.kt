package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentInsertCourseBinding
import com.example.diplomski_android.model.Language
import kotlinx.android.synthetic.main.fragment_insert_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsertCourseFragment: Fragment() {

    private val mainViewModel : MainViewModel by activityViewModels()
    private var insertCourseBinding: FragmentInsertCourseBinding? = null
    var languages = listOf<Language>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentInsertCourseBinding.inflate(inflater, container, false)
        insertCourseBinding = fragmentBinding

        CoroutineScope(Dispatchers.IO).launch {
            languages = mainViewModel.getLanguages()
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()

        insertCourseBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            insertCourseFragment = this@InsertCourseFragment
        }
    }

    fun setAdapters(){
        val localAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, languages)
        actv_local.setAdapter(localAdapter)

        val foreignAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, languages)
        actv_foreign.setAdapter(foreignAdapter)

//      set initial text
        if (mainViewModel.newCourse.value?.id == null){
            actv_local.setText("",false)
            actv_foreign.setText("",false)
        }else{
            actv_local.setText(mainViewModel.newCourse.value?.local_language?.name,false)
            actv_foreign.setText(mainViewModel.newCourse.value?.foreign_language?.name,false)
        }

//      TODO: Ne radi poziv viewmodel funkcije iz layout-a: onItemSelected
        actv_local.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onLocalLanguageItemSelected(localAdapter, position)
        }

        actv_foreign.setOnItemClickListener { _, _, position, _ ->
            mainViewModel.onForeignLanguageItemSelected(foreignAdapter, position)
        }
    }
}
