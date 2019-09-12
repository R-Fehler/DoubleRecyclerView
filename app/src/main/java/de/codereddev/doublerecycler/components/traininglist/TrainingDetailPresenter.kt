package de.codereddev.doublerecycler.components.traininglist

import de.codereddev.doublerecycler.model.Exercise
import de.codereddev.doublerecycler.model.Training
import de.codereddev.doublerecycler.realm.DoubleRecyclerRealm
import io.realm.Realm
import java.lang.ref.WeakReference

class TrainingDetailPresenter(private val trainingUuid: String) {

    private lateinit var realm: Realm
    private var view: WeakReference<TrainingDetailView>? = null

    interface TrainingDetailView {
        fun onExerciseListRetrieved(exercises: List<Exercise>)
    }

    fun bind(view: TrainingDetailView) {
        this.view = WeakReference(view)
        realm = DoubleRecyclerRealm.newRealmInstance()
    }

    fun unbind() {
        realm.close()
    }

    fun retrieveExerciseList() {
        val training = realm.where(Training::class.java).equalTo("uuid", trainingUuid).findFirst()
            ?: return
        view?.get()?.onExerciseListRetrieved(training.exercises)
    }
}