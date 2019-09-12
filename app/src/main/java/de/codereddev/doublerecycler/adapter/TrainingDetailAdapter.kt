package de.codereddev.doublerecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.codereddev.doublerecycler.R
import de.codereddev.doublerecycler.model.Exercise

class TrainingDetailAdapter : RecyclerView.Adapter<TrainingDetailAdapter.ViewHolder>() {

    interface TrainingClickListener {
        fun onClick(uuid: String, adapter: ExerciseSetAdapter)
    }

    private val exerciseList: MutableList<Exercise> = mutableListOf()

    private val viewPool = RecyclerView.RecycledViewPool()

    private var listener: TrainingClickListener? = null

    fun setOnClickListener(listener: TrainingClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exercise_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        exerciseList[position].let { exercise ->
            val childManager = LinearLayoutManager(holder.recyclerView.context)
            val childAdapter = ExerciseSetAdapter(exercise.sets)

            holder.recyclerView.apply {
                layoutManager = childManager
                adapter = childAdapter
                setRecycledViewPool(viewPool)
            }

            holder.addBtn.setOnClickListener {
                // TODO: callback to add set in realm
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.trainingSetRv)
        val addBtn: Button = itemView.findViewById(R.id.addSetBtn)
    }

    fun swapData(data: List<Exercise>) {
        exerciseList.clear()
        exerciseList.addAll(data)
        notifyDataSetChanged()
    }
}