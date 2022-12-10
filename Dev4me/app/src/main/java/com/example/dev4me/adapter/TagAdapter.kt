package com.example.dev4me.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.R
import com.example.dev4me.Tag
import com.google.android.material.chip.Chip

class TagAdapter(
    private val itemsList: List<Tag>,
    private val function: () -> Unit
) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    inner class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tag: Chip = view.findViewById(R.id.tag)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.res_tag, parent, false)
        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item = itemsList[position]

        holder.tag.text = item.nome

        setListeners(holder, item)
    }

    private fun setListeners(holder: TagViewHolder, userCard: Tag) {
        holder.tag.setOnClickListener {
            if (holder.tag.isChecked) {

            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): Tag {
        return itemsList[position]
    }
}