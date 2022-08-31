package com.sangeetha.movie_sample.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class WrapperRecyclerAdapter<T>(
    private val layoutId: Int,
    private val data: List<T>,
) : RecyclerView.Adapter<WrapperRecyclerAdapter<T>.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {
            setDataToView(itemView, item)
        }
    }

    abstract fun setDataToView(itemView: View, item: T)
}