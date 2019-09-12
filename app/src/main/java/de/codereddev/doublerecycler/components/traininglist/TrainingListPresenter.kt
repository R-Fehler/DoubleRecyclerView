package de.codereddev.doublerecycler.components.traininglist

import de.codereddev.doublerecycler.model.Training
import de.codereddev.doublerecycler.realm.DoubleRecyclerRealm
import io.realm.Realm
import java.lang.ref.WeakReference

class TrainingListPresenter {

    private lateinit var realm: Realm
    private var view: WeakReference<TrainingListView>? = null

    interface TrainingListView {
        fun onTrainingCreated(trainingUuid: String)
    }

    fun bind(view: TrainingListView) {
        this.view = WeakReference(view)
        realm = DoubleRecyclerRealm.newRealmInstance()
    }

    fun unbind() {
        realm.close()
    }

    fun createTraining() {
        realm.executeTransactionAsync {
            val training = Training()
            it.copyToRealm(training)

            view?.get()?.onTrainingCreated(training.uuid)
        }
    }
}