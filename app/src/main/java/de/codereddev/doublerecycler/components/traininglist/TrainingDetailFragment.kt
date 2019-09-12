package de.codereddev.doublerecycler.components.traininglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.codereddev.doublerecycler.R
import de.codereddev.doublerecycler.adapter.TrainingDetailAdapter
import de.codereddev.doublerecycler.model.Exercise

class TrainingDetailFragment : Fragment(), TrainingDetailPresenter.TrainingDetailView {

    private val parentAdapter = TrainingDetailAdapter()

    private lateinit var presenter: TrainingDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null)
            throw NullPointerException("Could not be started without arguments")

        presenter = TrainingDetailPresenter(arguments!!.getString(EXTRA_UUID)!!)
        presenter.bind(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_training_detail , container, false)

        rootView.findViewById<RecyclerView>(R.id.exerciseRv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = parentAdapter
        }

        rootView.findViewById<TextView>(R.id.trainingUuidTv).setText(arguments!!.getString(EXTRA_UUID))

        return rootView
    }

    override fun onStart() {
        super.onStart()

        presenter.retrieveExerciseList()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun onExerciseListRetrieved(exercises: List<Exercise>) {
        parentAdapter.swapData(exercises)
    }

    companion object {
        private const val EXTRA_UUID = "uuid"

        fun getInstance(trainingUuid: String): TrainingDetailFragment {
            val fragment = TrainingDetailFragment()
            val args = bundleOf(EXTRA_UUID to trainingUuid)
            fragment.arguments = args
            return fragment
        }
    }
}