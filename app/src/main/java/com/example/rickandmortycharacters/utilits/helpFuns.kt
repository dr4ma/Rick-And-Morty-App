package com.example.rickandmortycharacters.utilits

import android.content.Context
import android.widget.Toast
import com.example.rickandmortycharacters.R
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