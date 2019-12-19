package de.codereddev.doublerecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.codereddev.doublerecycler.adapter.TrainingSetAdapter
import de.codereddev.doublerecycler.adapter.TrainingAdapter
import de.codereddev.doublerecycler.model.TrainingSet
import de.codereddev.doublerecycler.model.Training
import de.codereddev.doublerecycler.realm.DoubleRecyclerRealm
import io.realm.Realm
import java.util.*

class MainActivity : AppCompatActivity(), TrainingAdapter.TrainingClickListener {

    private var recyclerView: RecyclerView? = null
    private val parentAdapter = TrainingAdapter()

    // implementiere die onClick Interface fun von TrainingAdapter
    override fun onClick(uuid: String, editText: EditText, adapter: TrainingSetAdapter) {
        if (editText.text.isEmpty())
            return

        editText.text.let {
            // wenn text nicht leer ist adde ein Set zu Training Objekt mit name == it
            val set = addSetToTraining(uuid, it.toString()) //returns a TrainingSet nachdem dieses im Realm
            // im passenden Training des Realms gespeichert wurde
            editText.text.clear()
            adapter.addTrainingSet(set) // füge es der Liste des Adapters hinzu und notify das es geändert ist
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // übergebe den einzelnen parent RV items den in der MainAct. impl. onClick
        // this ist hier die MainAct. die TrainingAdapter.TrainingClickListener ja extended bzw implementiert
        parentAdapter.setOnClickListener(this)


        // hier wird der Parent RV Adapter zugewiesen (nachdem der onClickListener übergeben /gesetzt wurde
        recyclerView = findViewById<RecyclerView>(R.id.outterRv).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = parentAdapter
        }

        // hier wird der FAB rechts unten initialisiert
        findViewById<FloatingActionButton>(R.id.addFab).apply {
            // soll ein Training adden und die View updaten
            setOnClickListener {
                addTraining() //erstellt ein neues Realm Training Objekt. nicht in Memory vom Adapter!
                reloadData()
            }
        }
        // das erste mal wurde ja kein knopf gedrückt also wird hier das erste mal die View upgedated
        // mit Daten aus der Realm DB
        reloadData()
    }

    fun addSetToTraining(uuid: String, text: String): TrainingSet {
        DoubleRecyclerRealm.newRealmInstance().use {
            val set = TrainingSet().apply {
                this.text = text // init den Set mit dem namen text
            }

            it.executeTransaction { realm ->
                val managedSet = realm.copyToRealm(set) // kopiert den Set in die Realm Instanz
                // wo das Training diesselbe ID hat wird ein
                // Realm.Query zurück gegeben (= hier vom Typ Training)
                // wird der Set eingefügt (somit korrektes Datum etc)
                val training: Training? = realm.where(Training::class.java).equalTo("uuid", uuid).findFirst()
                // hier wird dem Training aus dem realm der Satz hinzugefügt
                training?.sets?.add(managedSet)
            }
            // gebe set mit dem namen initialisiert zurück
            return set
        }
    }

    fun addTraining() {
        DoubleRecyclerRealm.newRealmInstance().use {
            it.executeTransaction { realm ->
                realm.createObject(Training::class.java, UUID.randomUUID().toString())
            }
        }
    }

    fun addDbData() {
        DoubleRecyclerRealm.newRealmInstance().use {
            it.executeTransaction {
                val training = it.createObject(Training::class.java, UUID.randomUUID().toString())
                val setList = mutableListOf<TrainingSet>()
                for (i in 1..10) {
                    setList.add(TrainingSet(    ).apply {
                        text = "Set $i"
                    })
                }

                training.sets.addAll(it.copyToRealm(setList))
            }
        }
    }

    fun reloadData() {
        DoubleRecyclerRealm.newRealmInstance().use {
            // sucht alle Training Objekte in Realm un gibt sie an den parentAdapter zurück
            val result = it.where(Training::class.java).findAll()
            /*copyFromRealm Makes an unmanaged in-memory copy of already persisted RealmObjects. This is a deep copy that will copy all referenced objects.
            //The copied objects are all detached from Realm and they will no longer be automatically updated.
            */
            parentAdapter.swapData(it.copyFromRealm(result))
        }
    }
}
