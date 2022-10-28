package com.example.dev4me

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.databinding.ResCardJobBinding
import com.example.dev4me.endpoints.Vaga

class AdapterJobs(private val context: Context, private val jobsList: List<Vaga>):
    RecyclerView.Adapter<AdapterJobs.JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
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