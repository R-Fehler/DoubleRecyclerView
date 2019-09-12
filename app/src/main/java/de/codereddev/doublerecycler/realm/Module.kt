package de.codereddev.doublerecycler.realm

import de.codereddev.doublerecycler.model.Exercise
import de.codereddev.doublerecycler.model.Training
import de.codereddev.doublerecycler.model.ExerciseSet
import de.codereddev.doublerecycler.model.KnownExercise
import io.realm.annotations.RealmModule

@RealmModule(classes = [Training::class, KnownExercise::class, Exercise::class, ExerciseSet::class])
class Module