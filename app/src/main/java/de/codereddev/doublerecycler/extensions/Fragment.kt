package de.codereddev.doublerecycler.extensions

import androidx.fragment.app.Fragment

fun Fragment.runOnUiThread(block: () -> Unit) {
    activity?.runOnUiThread {
        block()
    }
}