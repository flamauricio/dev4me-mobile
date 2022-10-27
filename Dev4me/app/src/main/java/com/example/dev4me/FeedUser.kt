package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dev4me.databinding.ActivityFeedUserBinding
import com.example.dev4me.endpoints.Vaga
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedUser : AppCompatActivity() {

    private lateinit var binding: ActivityFeedUserBinding
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecyclerView()
        bringJobs()

//        binding.feedUserCard1.setOnClickListener {
//            val navigateToOpenedCard = Intent(this, OpenedCardJob::class.java)
//            startActivity(navigateToOpenedCard)
//        }

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

    }

    private fun configRecyclerView() {
        // Trazer lista de vagas

    }

    private fun bringJobs() {
        val jobRequest = retrofit.create(
            Vaga::class.java
        )

        jobRequest.getVagas().enqueue(
            object : Callback<List<JsonObject>> {
                override fun onResponse(
                    call: Call<List<JsonObject>>,
                    response: Response<List<JsonObject>>
                ) {
                    if (response.code() == 200) {
                        // mostra as vagas no feed do usuário
                    }
                }

                override fun onFailure(call: Call<List<JsonObject>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedUser)
                        .setMessage(resources.getString(R.string.api_error))
                        .show()
                }
            }
        )
    }
}