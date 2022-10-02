package com.example.rickandmortycharacters.utilits

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.rickandmortycharacters.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun showToast(message: String){
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}

fun downloadIcon(circleImageView: CircleImageView, path: String) {
    Picasso.get().load(path)
        .placeholder(R.drawable.user)
        .error(R.drawable.user)
        .into(circleImageView)
}

fun showSnackBar(view: View, title: String, color: Int) {
    val snack = Snackbar.make(view, title, Snackbar.LENGTH_SHORT)
        .setTextColor(Color.WHITE)
        .setBackgroundTint(APP_ACTIVITY.getColor(color))
        .setActionTextColor(APP_ACTIVITY.getColor(R.color.white))
    snack.setAction(APP_ACTIVITY.getString(R.string.cancel)) {
        snack.dismiss()
    }
    snack.show()
}