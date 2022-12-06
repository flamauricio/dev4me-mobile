package com.example.dev4me.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.R
import com.example.dev4me.dto.UserRequest

class UserAdapter(
    private val itemsList: List<UserRequest>,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: View = view.findViewById(R.id.personCard)
        val imageUser: ImageView = view.findViewById(R.id.imageUser)
        val userName: TextView = view.findViewById(R.id.nameUser)
        val localization: TextView = view.findViewById(R.id.localization)
        val userDescription: TextView = view.findViewById(R.id.userDescription)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.res_card_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = itemsList[position]

        holder.userName.text = item.nome
        holder.localization.text = item.cep
        holder.userDescription.text = item.descUsuario

        setListeners(holder, item)
    }

    private fun setListeners(holder: UserViewHolder, userCard: UserRequest) {
        holder.card.setOnClickListener {
            // abrir card c o id
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): UserRequest {
        return itemsList[position]
    }
}