package de.codereddev.doublerecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.codereddev.doublerecycler.R
import de.codereddev.doublerecycler.model.ExerciseSet

class ExerciseSetAdapter(
    private val exerciseSetList: MutableList<ExerciseSet> = mutableListOf()
) : RecyclerView.Adapter<ExerciseSetAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setNumTv: TextView = itemView.findViewById(R.id.setNumTv)
        val weightEdit: EditText = itemView.findViewById(R.id.weightEdit)
        val repsEdit: EditText = itemView.findViewById(R.id.repsEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.child_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exerciseSetList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        exerciseSetList[position].let {
            holder.setNumTv.text = (position + 1).toString()

            it.weight?.let { weight ->
                holder.weightEdit.setText(weight.toString())
            }

            if (it.reps > 0) {
                holder.repsEdit.setText(it.reps.toString())
            }
        }
    }

    fun swapData(data: List<ExerciseSet>) {
        exerciseSetList.clear()
        exerciseSetList.addAll(data)
        notifyDataSetChanged()
    }

    fun addExerciseSet(set: ExerciseSet) {
        exerciseSetList.add(set)
        notifyItemInserted(itemCount - 1)
    }
}