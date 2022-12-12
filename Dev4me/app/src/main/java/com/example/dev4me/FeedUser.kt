package com.example.dev4me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev4me.adapter.JobsAdapter
import com.example.dev4me.databinding.ActivityFeedUserBinding
import com.example.dev4me.endpoints.Tag
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedUser : AppCompatActivity() {

    private lateinit var binding: ActivityFeedUserBinding

    val retrofit = Rest.getInstance()
    var jobsList: List<com.example.dev4me.Vaga> = listOf()
    val context: Context = this
    var tagsList: List<com.example.dev4me.Tag> = listOf()
    var selectedTags: MutableList<com.example.dev4me.Tag> = ArrayList<com.example.dev4me.Tag>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bringJobs()
        getTags()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.barraFiltro.setOnClickListener {
            val visibility = binding.filterView.visibility
            if (visibility == View.GONE) {
                binding.filterView.visibility = View.VISIBLE
            } else {
                binding.filterView.visibility = View.GONE
            }
        }

        binding.searchButton.setOnClickListener {
            filtrar()
        }
    }

    private fun filtrar() {
        if (selectedTags.isEmpty()) {
            bringJobs()
            return
        }

        retrofit.create(com.example.dev4me.endpoints.Vaga::class.java).getFilteredJobs(selectedTags)
            .enqueue(object : Callback<List<Vaga>> {
                override fun onResponse(call: Call<List<Vaga>>, response: Response<List<Vaga>>) {
                    if (response.code() == 200) {
                        jobsList = response.body()!!
                        val layoutManager = LinearLayoutManager(this@FeedUser)
                        binding.recyclerViewFeedUser.layoutManager = layoutManager
                        val adapter = JobsAdapter(jobsList, context)
                        binding.recyclerViewFeedUser.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<Vaga>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedUser)
                        .setMessage(t.message)
                        .show()
                }
            })
    }

    private fun bringJobs() {
        val jobRequest = retrofit.create(
            com.example.dev4me.endpoints.Vaga::class.java
        )

        jobRequest.getVagas().enqueue(
            object : Callback<List<com.example.dev4me.Vaga>> {
                override fun onResponse(
                    call: Call<List<com.example.dev4me.Vaga>>,
                    response: Response<List<com.example.dev4me.Vaga>>
                ) {
                    if (response.code() == 200) {
                        jobsList = response.body()!!
                        val layoutManager = LinearLayoutManager(this@FeedUser)
                        binding.recyclerViewFeedUser.layoutManager = layoutManager
                        val adapter = JobsAdapter(jobsList, context)
                        binding.recyclerViewFeedUser.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<List<com.example.dev4me.Vaga>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedUser)
                        .setMessage(resources.getString(R.string.api_error))
                        .show()
                }
            }
        )
    }

    private fun getTags() {
        val getTagsRequest = retrofit.create(Tag::class.java)
        getTagsRequest.getTags().enqueue(object : Callback<List<com.example.dev4me.Tag>> {
            override fun onResponse(
                call: Call<List<com.example.dev4me.Tag>>,
                response: Response<List<com.example.dev4me.Tag>>
            ) {
                if (response.code() == 200) {
                    tagsList = response.body()!!
                    tagsList.forEach {
                        val tag = layoutInflater.inflate(R.layout.res_tag, null)
                        tag.findViewById<TextView>(R.id.tagName).text = it.nome

                        tag.setOnClickListener { v ->
                            if (!selectedTags.contains(it)) {
                                selectedTags.add(it)
                                tag.findViewById<TextView>(R.id.tagName).setTextColor(getResources().getColor(R.color.purple_200))
                            } else {
                                selectedTags.remove(it)
                                tag.findViewById<TextView>(R.id.tagName).setTextColor(getResources().getColor(R.color.white))
                            }
                        }

                        binding.tagsFiltro.addView(tag)
                    }
                }
            }

            override fun onFailure(call: Call<List<com.example.dev4me.Tag>>, t: Throwable) {
                MaterialAlertDialogBuilder(this@FeedUser).setMessage(t.message.toString())
                    .show()
            }
        })
    }
}