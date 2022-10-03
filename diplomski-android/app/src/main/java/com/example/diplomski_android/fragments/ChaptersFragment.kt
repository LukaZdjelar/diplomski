package com.example.diplomski_android.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomski_android.R
import com.example.diplomski_android.adapter.ChaptersAdapter
import com.example.diplomski_android.adapter.CoursesAdapter
import com.example.diplomski_android.adapter.LessonsAdapter
import com.example.diplomski_android.databinding.FragmentChaptersBinding
import com.example.diplomski_android.databinding.FragmentCoursesBinding
import com.example.diplomski_android.model.Chapter
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_chapters.*
import kotlinx.android.synthetic.main.fragment_courses.*
import kotlinx.android.synthetic.main.item_chapter.*

class ChaptersFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private var chaptersBinding : FragmentChaptersBinding? = null
    private lateinit var chaptersAdapter : ChaptersAdapter
    private lateinit var lessonsAdapter: LessonsAdapter

    //TODO test, izbrisati
    private var lessonsTest : List<Lesson> = listOf(Lesson(1,1, "AVAILABLE", "TRANSLATE", null)
                                                    ,Lesson(2,1, "AVAILABLE", "VOCABULARY", null)
                                                    ,Lesson(3,1, "AVAILABLE", "SPEAKING", null))

    private var lessonsTest2 : List<Lesson> = listOf(Lesson(4,2, "AVAILABLE", "TRANSLATE", null)
                                                    ,Lesson(5,2, "AVAILABLE", "LISTENING", null))

    private var chaptersTest : List<Chapter> = listOf(Chapter(1,1,"Osnove I",false, lessonsTest, 1)
                                                        ,Chapter(2,1,"Osnove II",false, lessonsTest2, 2))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        setupRecyclerView()

        chaptersAdapter.differ.submitList(chaptersTest)
//        lessonsAdapter.differ.submitList(lessonsTest)
    }

    private fun setupRecyclerView(){
        chaptersAdapter = ChaptersAdapter(mainViewModel)
        rvChapters.apply {
            adapter = chaptersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
//        lessonsAdapter = LessonsAdapter(mainViewModel)
//        rvLessons.apply {
//            adapter = lessonsAdapter
//            layoutManager = LinearLayoutManager(activity)
//        }
    }
}