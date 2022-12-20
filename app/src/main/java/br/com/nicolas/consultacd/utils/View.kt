package br.com.nicolas.consultacd.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnack(message: String, duration : Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

