package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityCompanyMenuBinding

class CompanyMenu : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.editPersonalData.setOnClickListener {
            val navigate = Intent(this, CompanyProfile::class.java)
            startActivity(navigate)
        }

        binding.changePassword.setOnClickListener {
            val navigate = Intent(this, MudarSenha::class.java)
            startActivity(navigate)
        }

        binding.myCompanyJobs.setOnClickListener {
            val navigate = Intent(this, CompanyPostedJobs::class.java)
            startActivity(navigate)
        }

        binding.creatJob.setOnClickListener {
            val navigate = Intent(this, CadastroVaga::class.java)
            startActivity(navigate)
        }

        binding.logOff.setOnClickListener {
            val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = prefs.edit()

            editor.putInt("id", 0)
            editor.putString("tipoUsuario", "")
            editor.commit()

            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.unableAccount.setOnClickListener {
            startActivity(Intent(this, DesativarConta::class.java))
        }

    }
}