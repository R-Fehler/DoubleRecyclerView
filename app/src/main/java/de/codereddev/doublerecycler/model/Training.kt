package de.codereddev.doublerecycler.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Training : RealmObject() {
    @PrimaryKey
    var uuid: String = UUID.randomUUID().toString()
    var notes: String? = null
    var date: Date = Date()
    //duration, location, time, blabla
    var exercises: RealmList<Exercise> = RealmList()
    var duration: Long = 0
}