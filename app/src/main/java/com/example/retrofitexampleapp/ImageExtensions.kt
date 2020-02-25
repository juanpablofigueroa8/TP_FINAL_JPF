package com.example.retrofitexampleapp

import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.http.Url

fun ImageView.fromUrl(url:String){
    Picasso.get().load(url).into(this)

}