package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dev4me.databinding.ActivityUserCandidaciesBinding

class UserCandidacies : AppCompatActivity() {

    private lateinit var binding: ActivityUserCandidaciesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCandidaciesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.candidacyCard1.setOnClickListener {
            val navigateToOpenedCard = Intent(this, OpenedCardJob::class.java)
            startActivity(navigateToOpenedCard)
        }
    }
}