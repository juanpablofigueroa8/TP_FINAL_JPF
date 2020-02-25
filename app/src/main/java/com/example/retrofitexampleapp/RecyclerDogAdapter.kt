package com.example.retrofitexampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_dog_item.view.*


class RecyclerDogAdapter (val images: List<String>) :
    RecyclerView.Adapter<RecyclerDogAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dog_item,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = images.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(images[position])
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(image: String){
                itemView.imageViewDog.fromUrl(image)
                 }
        }
    }
