package com.example.diplomski_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task
import kotlinx.android.synthetic.main.item_lesson.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LessonsAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    private lateinit var view: View
    inner class LessonsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return LessonsViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val lesson = differ.currentList[position]
        holder.itemView.apply {
            tvLessonName.text = lesson.lesson_type

            button_edit_lesson.setOnClickListener {
                mainViewModel.setNewLesson(lesson)
                Navigation.findNavController(view).navigate(R.id.action_lessonsFragment_to_insertLessonFragment)
            }
            button_delete_lesson.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.deleteLessonComplete(lesson)
                }
            }

            button_manage_tasks.setOnClickListener {
                mainViewModel.getTasksByLessonFlow(lesson.id!!)
                Navigation.findNavController(view).navigate(R.id.action_lessonsFragment_to_tasksFragment)
            }

//          TODO: ne radi Toast

            setOnClickListener{
                lateinit var tasks: List<Task>
                val job = CoroutineScope(Dispatchers.IO).launch {
                    tasks = mainViewModel.getTasksByLesson(lesson.id!!)
                }
                runBlocking { job.join() }
                if (tasks.isEmpty()){
                    Toast.makeText(context,"There are no tasks", Toast.LENGTH_SHORT).show()
                }else{
                    mainViewModel.setTasks(tasks)
                    Navigation.findNavController(view).navigate(R.id.action_lessonsFragment_to_taskFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}