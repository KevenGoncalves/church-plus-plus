package com.example.churchplusplus.Adapter.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.churchplusplus.R

class MemberViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.tipoText)
    val contact = view.findViewById<TextView>(R.id.contactText)
    val location = view.findViewById<TextView>(R.id.tipoText)
    val threedots = view.findViewById<ImageView>(R.id.imageView16)
}