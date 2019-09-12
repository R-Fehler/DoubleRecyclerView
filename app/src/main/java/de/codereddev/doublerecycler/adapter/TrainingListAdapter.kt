package de.codereddev.doublerecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.codereddev.doublerecycler.R
import de.codereddev.doublerecycler.model.Training

class TrainingListAdapter : RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    val trainingList: MutableList<Training> = mutableListOf()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numTv: TextView = itemView.findViewById(R.id.trainingNum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.training_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numTv.text = (position + 1).toString()
    }

    fun swapData(list: List<Training>) {
        trainingList.clear()
        trainingList.addAll(list)
        notifyDataSetChanged()
    }
}