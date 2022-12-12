package com.example.dev4me.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.OpenedCardJob
import com.example.dev4me.PersonProfileView
import com.example.dev4me.R
import com.example.dev4me.dto.UserRequest

class UserAdapter(
    private val itemsList: List<UserRequest>,
    private val context: Context,
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

        setListeners(holder, item, item.id)
    }

    private fun setListeners(holder: UserViewHolder, userCard: UserRequest, id: Integer?) {
        holder.card.setOnClickListener {

            val intent = Intent(context, PersonProfileView::class.java)
            intent.putExtra("id", id)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): UserRequest {
        return itemsList[position]
    }
}