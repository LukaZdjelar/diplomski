package com.example.diplomski_android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomski_android.MainViewModel
import com.example.diplomski_android.R
import com.example.diplomski_android.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<TaskAdapter.TasksViewHolder>() {

    private lateinit var view: View
    inner class TasksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

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
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = differ.currentList[position]
        holder.itemView.apply {
            tvTaskQuestion.text = task.question

            button_edit_task.setOnClickListener {
                mainViewModel.setNewTask(task)
                Navigation.findNavController(view).navigate(R.id.action_tasksFragment_to_insertTaskFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}