package com.example.todoapp

import android.app.Fragment
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class Adapter(var data: List<Entity>): RecyclerView.Adapter<Adapter.viewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter.viewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_main,parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: Adapter.viewHolder, position: Int) {
        holder.layout.setBackgroundColor(Color.parseColor("#263238"))
        holder.title.text = data[position].title

        val task = data[position] //get specific task

        holder.itemView.setOnClickListener {
            val bundle = android.os.Bundle().apply {
                putInt("taskId", task.id) //pass task id
            }

            findNavController(holder.itemView).navigate(R.id.action_displayTaskFragment_to_updateTaskFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<Entity>){
        data = newData
        notifyDataSetChanged()
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title: TextView =  itemView.findViewById(R.id.txtTitle)
        var layout: LinearLayout = itemView.findViewById(R.id.mylayout)
    }

}