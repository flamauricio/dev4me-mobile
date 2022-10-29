package com.example.dev4me

import android.annotation.SuppressLint
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
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {

        val faixaSalarialMin = jobsList.get(position).get("faixaSalarialMin").toString()
        val faixaSalarialMax = jobsList.get(position).get("faixaSalarialMax").toString()

        // imagem mockada
        holder.imageCompany.setImageResource(R.drawable.world)
        holder.localization.text = jobsList.get(position).get("localizacao").toString()
        //holder.nameCompany.text = jobsList.get(position).get("empresa.nome").toString()
        holder.jobTitle.text = jobsList.get(position).get("titulo").toString()
        holder.level.text = jobsList.get(position).get("level").toString()
        holder.contract.text = jobsList.get(position).get("contrato").toString()
        holder.salary.text = "R$ $faixaSalarialMin até $faixaSalarialMax"
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