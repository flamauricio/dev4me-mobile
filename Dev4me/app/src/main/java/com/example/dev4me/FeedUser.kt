package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev4me.adapter.JobsAdapter
import com.example.dev4me.databinding.ActivityFeedUserBinding
import com.example.dev4me.dto.JobRequest
import com.example.dev4me.endpoints.Vaga
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedUser : AppCompatActivity() {

    private lateinit var binding: ActivityFeedUserBinding

    val retrofit = Rest.getInstance()
    var jobsList: List<JobRequest> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bringJobs()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

    }

    private fun bringJobs() {
        val jobRequest = retrofit.create(
            Vaga::class.java
        )

        jobRequest.getVagas().enqueue(
            object : Callback<List<JobRequest>> {
                override fun onResponse(
                    call: Call<List<JobRequest>>,
                    response: Response<List<JobRequest>>
                ) {
                    if (response.code() == 200) {
                        jobsList = response.body()!!
                        MaterialAlertDialogBuilder(this@FeedUser)
                            .setMessage(jobsList.toString())
                            .show()
                        val layoutManager = LinearLayoutManager(this@FeedUser)
                        binding.recyclerViewFeedUser.layoutManager = layoutManager
                        val adapter = JobsAdapter(jobsList)
                        binding.recyclerViewFeedUser.adapter = adapter
                    }
                }
                override fun onFailure(call: Call<List<JobRequest>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedUser)
                        .setMessage(resources.getString(R.string.api_error))
                        .show()
                }
            }
        )
    }
}