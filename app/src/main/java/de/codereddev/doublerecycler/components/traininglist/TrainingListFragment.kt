package de.codereddev.doublerecycler.components.traininglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.codereddev.doublerecycler.R
import de.codereddev.doublerecycler.adapter.TrainingListAdapter
import de.codereddev.doublerecycler.extensions.runOnUiThread

class TrainingListFragment : Fragment(), TrainingListPresenter.TrainingListView {

    private val trainingAdapter = TrainingListAdapter()

    private lateinit var presenter: TrainingListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = TrainingListPresenter()
        presenter.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_training_list, container, false)

        rootView.findViewById<RecyclerView>(R.id.trainingRv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trainingAdapter
        }

        rootView.findViewById<FloatingActionButton>(R.id.addTrainingFab).setOnClickListener {
            presenter.createTraining()
        }

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun onTrainingCreated(trainingUuid: String) {
        runOnUiThread {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.container, TrainingDetailFragment.getInstance(trainingUuid))
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}