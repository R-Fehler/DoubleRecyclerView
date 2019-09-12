//package de.codereddev.doublerecycler.realm
//
//import de.codereddev.doublerecycler.model.Training
//import de.codereddev.doublerecycler.model.ExerciseSet
//
//object DatabaseHelper {
//
//    fun storeSet(trainingUuid: String, set: ExerciseSet) {
//        databaseTransaction {
//            val training = where(Training::class.java).equalTo("uuid", trainingUuid).findFirst()
//                ?: throw IllegalArgumentException("No training available with UUID $trainingUuid")
//
//            val managedSet = copyToRealm(set)
//            training.sets.add(managedSet)
//        }
//    }
//
//    fun fetchSets(trainingUuid: String): List<ExerciseSet> {
//        return copyListFromDatabase {
//            val training: Training? = where(Training::class.java).equalTo("uuid", trainingUuid).findFirst()
//                ?: throw IllegalArgumentException("No training available with UUID $trainingUuid")
//
//            training!!.sets
//        }
//    }
//
//    fun fetchTrainings(): List<Training> {
//        return copyListFromDatabase {
//            where(Training::class.java).findAll()
//        }
//    }
//}