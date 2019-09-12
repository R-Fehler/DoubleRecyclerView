package de.codereddev.doublerecycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.codereddev.doublerecycler.components.traininglist.TrainingListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, TrainingListFragment())
            .commit()
    }
}
