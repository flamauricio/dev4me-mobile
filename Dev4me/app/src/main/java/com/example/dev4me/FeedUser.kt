package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dev4me.databinding.ActivityFeedUserBinding

class FeedUser : AppCompatActivity() {

    private lateinit var binding: ActivityFeedUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configRecyclerView()

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
}