package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityFeedUserBinding
import com.example.dev4me.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrarBtn.setOnClickListener {
            val navigateToFeedUser = Intent(this, FeedUser::class.java)
            startActivity(navigateToFeedUser)
        }
    }
}