package de.codereddev.doublerecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.codereddev.doublerecycler.R
import de.codereddev.doublerecycler.model.Training

class TrainingAdapter : RecyclerView.Adapter<TrainingAdapter.ViewHolder>() {


    // interface fuer den add button auf jedem item in Rec View
    interface TrainingClickListener {
        fun onClick(uuid: String, editText: EditText, adapter: TrainingSetAdapter)
    }

    // Liste der Trainings: elemente der obersten RV
    private val trainingList: MutableList<Training> = mutableListOf()

    //TODO was ist das RecycledViewPool
    private val viewPool = RecyclerView.RecycledViewPool()

    private var listener: TrainingClickListener? = null

    fun setOnClickListener(listener: TrainingClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // child RV inside parent RV
        trainingList[position].let { training ->

            val childManager = LinearLayoutManager(holder.recyclerView.context)
            // Adapter Init  für jedes it==training mit it.sets
            val childAdapter = TrainingSetAdapter(training.sets)

            holder.recyclerView.apply {
                layoutManager = childManager
                adapter = childAdapter
                // TODO was macht das setRec..
                setRecycledViewPool(viewPool)
            }

            holder.addBtn.setOnClickListener {
                // call interface fun onClick von TrainingClickListener
                // uebergibt die UUID den edittext und den childRV Adapter des aktuellen training elements
                listener?.onClick(training.uuid, holder.editText, childAdapter)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**@see onBindViewHolder
         * hier ist der Trick: der ViewHolder des Parent RecView hält eine child RV
        */
        val recyclerView: RecyclerView = itemView.findViewById(R.id.innerRv)
        val editText: EditText = itemView.findViewById(R.id.editText)
        val addBtn: Button = itemView.findViewById(R.id.addSetBtn)
    }

    fun swapData(data: List<Training>) {
        trainingList.clear()
        trainingList.addAll(data)
        notifyDataSetChanged()
    }
}