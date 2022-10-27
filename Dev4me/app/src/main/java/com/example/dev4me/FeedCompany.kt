package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityFeedCompanyBinding

class FeedCompany : AppCompatActivity() {

    private lateinit var binding: ActivityFeedCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.personCard1.setOnClickListener {
//            val navigatePersonProfile = Intent(this, PersonProfileView::class.java)
//            startActivity(navigatePersonProfile)
//        }

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }
    }
}