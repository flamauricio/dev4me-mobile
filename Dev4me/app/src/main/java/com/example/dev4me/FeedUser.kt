package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityFeedUserBinding

class FeedUser : AppCompatActivity() {

    private lateinit var binding: ActivityFeedUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.feedUserCard1.setOnClickListener {
            val navigateToOpenedCard = Intent(this, OpenedCardJob::class.java)
            startActivity(navigateToOpenedCard)
        }
    }
}