package com.example.diplomski_android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.databinding.ItemTaskBinding
import com.example.diplomski_android.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    inner class TasksViewHolder(val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.tvTaskQuestion.text = task.question

            holder.binding.buttonEditTask.setOnClickListener {
                mainViewModel.setNewTask(task)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_tasksFragment_to_insertTaskFragment)
            }

            holder.binding.buttonDeleteTask.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    mainViewModel.deleteTask(task)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}