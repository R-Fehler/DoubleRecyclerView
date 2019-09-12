package de.codereddev.doublerecycler.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

open class ExerciseSet : RealmObject() {
    @PrimaryKey
    var uuid: String = UUID.randomUUID().toString()
    var weight: Int? = null
    var reps: Int = 0
    var duration: Long = 0
    //reps, time, duration, ...
}