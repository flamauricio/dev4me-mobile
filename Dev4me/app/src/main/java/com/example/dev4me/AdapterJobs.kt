package com.example.dev4me

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.databinding.ActivityFeedUserBinding
import com.example.dev4me.databinding.ResCardJobBinding
import com.example.dev4me.dto.JobRequest
import com.google.android.material.card.MaterialCardView
import com.google.gson.JsonObject

class AdapterJobs(private val jobsList: List<JsonObject>):
    RecyclerView.Adapter<AdapterJobs.JobViewHolder>() {

    // cria a lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.res_card_job, parent, false)
        return JobViewHolder(itemView)
    }

    // exibe a lista
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {

        val faixaSalarialMin = jobsList.get(position).get("faixaSalarialMin").toString()
        val faixaSalarialMax = jobsList.get(position).get("faixaSalarialMax").toString()
        val empresa: JsonObject = jobsList.get(position).get("empresa") as JsonObject

        // imagem mockada
        // holder.imageCompany.setImageResource(R.drawable.world)
        holder.localization.text = jobsList.get(position).get("localizacao").toString()
        holder.nameCompany.text = empresa.get("nome").toString()
        holder.jobTitle.text = jobsList.get(position).get("titulo").toString()
        holder.level.text = jobsList.get(position).get("level").toString()
        holder.contract.text = jobsList.get(position).get("contrato").toString()
        holder.salary.text = "R$ $faixaSalarialMin até $faixaSalarialMax"
    }

    override fun getItemCount(): Int {
        return jobsList.size
    }

     class JobViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val card : MaterialCardView = itemView.findViewById(R.id.feedUserCard)
        val nameCompany : TextView = itemView.findViewById(R.id.nameCompany)
        val jobTitle : TextView = itemView.findViewById(R.id.jobTitle)
        val bookmarkIcon : ImageView = itemView.findViewById(R.id.bookmarkIcon)
        val level : TextView = itemView.findViewById(R.id.level)
        val localization : TextView = itemView.findViewById(R.id.localization)
        val contract : TextView = itemView.findViewById(R.id.contract)
        val salary : TextView = itemView.findViewById(R.id.salary)
    }
}