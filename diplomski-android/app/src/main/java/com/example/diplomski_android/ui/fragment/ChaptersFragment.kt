package com.example.diplomski_android.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.FragmentChaptersBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.ui.adapter.ChaptersAdapter
import kotlinx.android.synthetic.main.fragment_chapters.*
import kotlinx.coroutines.flow.collectLatest

class ChaptersFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var chaptersBinding : FragmentChaptersBinding? = null
    private lateinit var chaptersAdapter : ChaptersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentChaptersBinding.inflate(inflater, container, false)
        chaptersBinding = fragmentBinding

        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chaptersBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mainViewModel
            chaptersFragment = this@ChaptersFragment
        }

        button_create_chapter.setOnClickListener {
            val chapter = Chapter()
            chapter.course = mainViewModel.currentCourse.value
            mainViewModel.setNewChapter(chapter)
            Navigation.findNavController(view).navigate(R.id.action_chaptersFragment_to_insertChapterFragment)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        chaptersAdapter = ChaptersAdapter(mainViewModel)
        rvChapters.apply {
            adapter = chaptersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getChaptersByCourseFlow(mainViewModel.currentCourse.value?.id!!)
        lifecycleScope.launchWhenCreated {
            mainViewModel.chaptersStateFlow.collectLatest {
                chaptersAdapter.differ.submitList(it)
            }
        }
    }
}