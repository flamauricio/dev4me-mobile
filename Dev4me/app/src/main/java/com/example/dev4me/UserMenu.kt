package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityUserMenuBinding

class UserMenu : AppCompatActivity() {

    private lateinit var binding: ActivityUserMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.editPersonalData.setOnClickListener {
            val navigate = Intent(this, UserProfile::class.java)
            startActivity(navigate)
        }

        binding.changePassword.setOnClickListener {
            val navigate = Intent(this, MudarSenha::class.java)
            startActivity(navigate)
        }

        binding.myApplyedJobs.setOnClickListener {
            val navigate = Intent(this, UserCandidacies::class.java)
            startActivity(navigate)
        }

        binding.unableAccount.setOnClickListener {
            // Page not done yet
            // val navigate = Intent(this, UserCandidacies::class.java)
            // startActivity(navigate)
        }

        binding.logOff.setOnClickListener {
            // Page not done yet
            // val navigate = Intent(this, UserCandidacies::class.java)
            // startActivity(navigate)
        }

    }
}