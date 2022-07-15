package com.example.openeye.utils

import android.widget.Toast
import com.example.openeye.App

fun toast(s: CharSequence) {
    Toast.makeText(App.appContext, s, Toast.LENGTH_SHORT).show()
}

fun toastLong(s: CharSequence) {
    Toast.makeText(App.appContext, s, Toast.LENGTH_LONG).show()
}

fun String.toast() = toast(this)
fun String.toastLong() = toastLong(this)