package com.example.dev4me

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.databinding.ResCardJobBinding
import com.example.dev4me.dto.JobRequest
import com.google.gson.JsonObject

class AdapterJobs(private val context: Context, private val jobsList: List<JsonObject>):
    RecyclerView.Adapter<AdapterJobs.JobViewHolder>() {

    // cria a lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val jobsList = ResCardJobBinding.inflate(LayoutInflater.from(context), parent, false)
        return JobViewHolder(jobsList)
    }

    // exibe a lista
    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        // pega a imagem da empresa
        // holder.imageCompany.setImageURI() = jobsList[position]
        holder.imageCompany.setImageResource(R.drawable.world)
//        holder.nameCompany.text = jobsList[position].nomeEmpresa
//        holder.jobTitle.text = jobsList[position].titulo
//        holder.level.text = jobsList[position].level
//        holder.localization.text = jobsList[position].localizacao
//        holder.contract.text = jobsList[position].contrato
        // implementar String formatada da faixa salarial
        holder.salary.text = "Salário Min - Salário Max"
    }

    override fun getItemCount(): Int {
        return jobsList.size
    }

    inner class JobViewHolder(binding: ResCardJobBinding): RecyclerView.ViewHolder(binding.root) {
        val card = binding.feedUserCard
        val imageCompany = binding.imageCompany
        val nameCompany = binding.nameCompany
        val jobTitle = binding.jobTitle
        val bookmarkIcon = binding.bookmarkIcon
        val level = binding.level
        val localization = binding.localization
        val contract = binding.contract
        val salary = binding.salary
    }
}