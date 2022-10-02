package com.example.dev4me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityOpenedCardJobBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OpenedCardJob : AppCompatActivity() {

    private lateinit var binding: ActivityOpenedCardJobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenedCardJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.applyButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.alert_title))
                .setMessage(resources.getString(R.string.alert_message))
                .setNegativeButton(resources.getString(R.string.alert_no)) { dialog, which ->
                    // Do nothing
                }
                .setPositiveButton(resources.getString(R.string.alert_yes)) { dialog, which ->
                    // Notify company and add to user candidacies
                    // Disable "apply now" button
                }
                .show()
        }
    }
}