package com.example.dev4me.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.R
import com.example.dev4me.dto.JobRequest

class JobsAdapter(
    private val itemsList: List<JobRequest>,
) : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    inner class JobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardJob: View = view.findViewById(R.id.feedUserCard)
        val imageCompany: ImageView = view.findViewById(R.id.imageCompany)
        val nameCompany: TextView = view.findViewById(R.id.nameCompany)
        val jobTitle: TextView = view.findViewById(R.id.jobTitle)
        val level: TextView = view.findViewById(R.id.level)
        val localizationJob: TextView = view.findViewById(R.id.localizationJob)
        val contract: TextView = view.findViewById(R.id.contract)
        val salary: TextView = view.findViewById(R.id.salary)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.res_card_job, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = itemsList[position]

        val salaryMin = item.faixaSalarialMin
        val salaryMax = item.faixaSalarialMin

        holder.nameCompany.text = item.nomeEmpresa
        holder.jobTitle.text = item.titulo
        holder.level.text = item.level
        holder.localizationJob.text = item.localizacao
        holder.contract.text = item.contrato
        holder.salary.text = "R\$ $salaryMin até $salaryMax"

        setListeners(holder, item)
    }

    private fun setListeners(holder: JobViewHolder, userCard: JobRequest) {
        holder.cardJob.setOnClickListener {
            // abrir card com o id
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): JobRequest {
        return itemsList[position]
    }
}