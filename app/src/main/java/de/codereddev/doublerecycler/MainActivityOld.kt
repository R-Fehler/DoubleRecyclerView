//package de.codereddev.doublerecycler
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.EditText
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import de.codereddev.doublerecycler.adapter.ExerciseSetAdapter
//import de.codereddev.doublerecycler.adapter.TrainingAdapter
//import de.codereddev.doublerecycler.model.ExerciseSet
//import de.codereddev.doublerecycler.model.Training
//import de.codereddev.doublerecycler.realm.DoubleRecyclerRealm
//import io.realm.Sort
//import java.util.*
//
//class MainActivityOld : AppCompatActivity(), TrainingAdapter.TrainingClickListener {
//
//    private var recyclerView: RecyclerView? = null
//    private val parentAdapter = TrainingAdapter()
//
//    override fun onClick(uuid: String, editText: EditText, adapter: ExerciseSetAdapter) {
//        if (editText.text.isEmpty())
//            return
//
//        editText.text.let {
//            val set = addSetToTraining(uuid, it.toString())
//            editText.text.clear()
//            adapter.addExerciseSet(set)
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        parentAdapter.setOnClickListener(this) //was macht das ?
//
//        recyclerView = findViewById<RecyclerView>(R.id.outterRv).apply {
//            layoutManager = LinearLayoutManager(this@MainActivityOld)
//            adapter = parentAdapter
//        }
//
//        findViewById<FloatingActionButton>(R.id.addFab).apply {
//            setOnClickListener {
//                addTraining()
//                reloadData()
//            }
//        }
//
//        reloadData()
//    }
//
//    //-------------------------------------------------
//
////    Kotlin Use function:
////    Kotlin Use function is an inline function used to execute
////    given block function on this resource. Use function closes the resource
////    correctly once after the operation is completed.
////    There is an added benefit that even if there is an exception while
////    executing the given block function, it is expected that the resource is
////    closed down correctly.
////
////    Use function could be thought of as try with resource in Java.
//    //-------------------------------------------------------------
//
//    fun addSetToTraining(uuid: String, text: String): ExerciseSet {
//        DoubleRecyclerRealm.newRealmInstance().use {
//            val set = ExerciseSet().apply {
//                this.text = text
//            }
//            // ist das kotlin spez.?? oder wieso wird der Wert so gesetzt?
//            // Normal approach
////------------------------------------------------------------------------------
////            fun createInstance(args: Bundle) : MyFragment {
////                val fragment = MyFragment()
////                fragment.arguments = args
////                return fragment
////            }
////            // Improved approach
////            fun createInstance(args: Bundle)
////                    = MyFragment().apply { arguments = args }
////            -----------------------------------------------------
//            it.executeTransaction { realm ->
//                val managedSet = realm.copyToRealm(set)
//                val training: Training? = realm.where(Training::class.java).equalTo("uuid", uuid).findFirst()
//                training?.sets?.add(managedSet) // warum Training? und nicht RealmResults
//            }
//            return set
//        }
//    }
//
//    fun addTraining() {
//        DoubleRecyclerRealm.newRealmInstance().use {
//            it.executeTransaction { realm ->
//                realm.createObject(Training::class.java, UUID.randomUUID().toString())
//            }
//        }
//    }
//
//    fun addDbData() {
//        DoubleRecyclerRealm.newRealmInstance().use {
//            it.executeTransaction {
//                val training = it.createObject(Training::class.java, UUID.randomUUID().toString())
//                val setList = mutableListOf<ExerciseSet>()
//                for (i in 1..10) {
//                    setList.add(ExerciseSet(    ).apply {
//                        text = "Set $i"
//                    })
//                }
//
//                training.sets.addAll(it.copyToRealm(setList))
//            }
//        }
//    }
//
//    fun reloadData() {
//        DoubleRecyclerRealm.newRealmInstance().use {
//            val result = it.where(Training::class.java).sort("date", Sort.DESCENDING).findAll()
//            parentAdapter.swapData(it.copyFromRealm(result))
//        }
//    }
//}
