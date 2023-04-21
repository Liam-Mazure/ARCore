package com.example.arcore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ObjAdapter(private val context: Context, private val objList: List<String>) : RecyclerView.Adapter<ObjAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.`object`, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = objList[position]

        // Load the image using Glide or any other image loading library
        Glide.with(context)
            .load(obj)
            .into(holder.imageView)

        // Set a click listener for the image view
        holder.imageView.setOnClickListener {
            // Load the obj file and place it in the scene
            // You can use the same code as before to load the obj file
        }
    }

    override fun getItemCount() = objList.size
}
