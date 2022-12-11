package com.example.dev4me.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.OpenedCardJob
import com.example.dev4me.R
import com.example.dev4me.Vaga

class JobsAdapter(
    private val itemsList: List<Vaga>,
    private val context: Context,
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
        val salaryMax = item.faixaSalarialMax

        holder.nameCompany.text = item.fkEmpresa.nome
        holder.jobTitle.text = item.titulo
        holder.level.text = item.level
        holder.localizationJob.text = item.localizacao
        holder.contract.text = item.contrato
        holder.salary.text = "R\$ $salaryMin até $salaryMax"

        setListeners(holder, item, item.idVaga)
    }

    private fun setListeners(holder: JobViewHolder, cardJob: Vaga, idVaga: Integer?) {
        holder.cardJob.setOnClickListener {

            val intent = Intent(context, OpenedCardJob::class.java)
            intent.putExtra("idVaga", idVaga)
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): Vaga {
        return itemsList[position]
    }
}