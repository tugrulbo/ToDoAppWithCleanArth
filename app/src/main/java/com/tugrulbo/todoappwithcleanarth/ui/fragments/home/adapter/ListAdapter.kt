package com.tugrulbo.todoappwithcleanarth.ui.fragments.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugrulbo.todoappwithcleanarth.R
import com.tugrulbo.todoappwithcleanarth.data.model.Priority
import com.tugrulbo.todoappwithcleanarth.data.model.ToDoModel

class ListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){}

    private val differCallback = object : DiffUtil.ItemCallback<ToDoModel>(){
        override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var data = differ.currentList[position]
        holder.itemView.apply {
            val tvTitle: TextView = this.findViewById(R.id.tvTitle)
            val tvDesc:TextView = this.findViewById(R.id.tvDesc)
            val card:CardView = this.findViewById(R.id.cVPriorityIndicator)
            val clickLayout:ConstraintLayout = this.findViewById(R.id.contraintLRowBg)
            val priority = data?.priority

            tvTitle.text = data?.todoName
            tvDesc.text = data?.desc
            when(priority){
                Priority.HIGH -> card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.red))
                Priority.MEDIUM -> card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.yellow))
                Priority.LOW -> card.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.green))
            }

            clickLayout.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("data",data)
                }

                findNavController().navigate(R.id.action_listFragment_to_updateListFragment, bundle)
            }


        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(data:List<ToDoModel>){
        this.differ.submitList(data)
        notifyDataSetChanged()
    }
}