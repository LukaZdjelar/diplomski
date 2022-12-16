package com.example.diplomski_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.ItemLessonBinding
import com.example.diplomski_android.model.Lesson
import com.example.diplomski_android.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LessonsAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder>() {

    inner class LessonsViewHolder(val binding: ItemLessonBinding): RecyclerView.ViewHolder(binding.root)

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
        return LessonsViewHolder(ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        val lesson = differ.currentList[position]
        var lessonsStatus = ""
        if (lesson.isCompleted == true) {
            lessonsStatus = "Completed"
        }
        holder.itemView.apply {
            holder.binding.llLessonItemAdmin.isVisible = mainViewModel.user.value?.admin!!
            holder.binding.tvLessonName.text = lesson.lesson_type
            holder.binding.tvLessonStatus.text = lessonsStatus

            setOnClickListener{
                lateinit var tasks: List<Task>
                CoroutineScope(Dispatchers.IO).launch {
                    tasks = mainViewModel.getTasksByLesson(lesson.id!!)
                    CoroutineScope(Dispatchers.Main).launch{
                        if (tasks.isEmpty()){
                            Toast.makeText(context,"There are no tasks", Toast.LENGTH_SHORT).show()

                        } else{
                            mainViewModel.setCurrentLesson(lesson)
                            mainViewModel.setTasks(tasks)
                            Navigation.findNavController(holder.itemView).navigate(R.id.action_lessonsFragment_to_taskFragment)
                        }
                    }
                }
            }

            holder.binding.buttonEditLesson.setOnClickListener {
                mainViewModel.setNewLesson(lesson)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_lessonsFragment_to_insertLessonFragment)
            }

            holder.binding.buttonDeleteLesson.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.deleteLessonComplete(lesson)
                }
            }

            holder.binding.buttonManageTasks.setOnClickListener {
                mainViewModel.setCurrentLesson(lesson)
                mainViewModel.getTasksByLessonFlow(lesson.id!!)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_lessonsFragment_to_tasksFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}