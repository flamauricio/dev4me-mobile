package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityCompanyMenuBinding
import com.example.dev4me.dto.EmpresaResponse
import com.example.dev4me.endpoints.Empresa
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyMenu : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyMenuBinding
    private var retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val id: Int = prefs.getInt("id", 0)

        if (id != 0) {
            pegarDadosEmpresa(id)
        }

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

            editor.putString("tipoUsuario", "")
            editor.commit()

            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.unableAccount.setOnClickListener {
            startActivity(Intent(this, DesativarConta::class.java))
        }

    }

    private fun pegarDadosEmpresa(id: Int) {
        val getEmpresaRequest = retrofit.create(
            Empresa::class.java
        )
        getEmpresaRequest.getEmpresaById(id).enqueue(object : Callback<JsonObject?> {
            override fun onResponse(
                call: Call<JsonObject?>,
                response: Response<JsonObject?>
            ) {
                if (response.code() == 200) {
                    plotarDadosNaTela(response.body())
                }
            }

            override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                MaterialAlertDialogBuilder(this@CompanyMenu)
                    .setMessage(t.message)
                    .show()
            }
        })
    }

    private fun plotarDadosNaTela(usuario: JsonObject?) {
        val nome: String = usuario?.get("nome").toString()
        val email: String = usuario?.get("email").toString()
        val cnpj: String = usuario?.get("cnpj").toString()

        binding.nome.text = nome.substring(1, nome.length-1)
        if (cnpj != "null") binding.cnpj.text = cnpj.substring(1, cnpj.length-1)

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("nome", nome.substring(1, nome.length-1))
        editor.putString("email", email.substring(1, email.length-1))
        editor.putString("cnpj", cnpj.substring(1, cnpj.length-1))
        editor.commit()
    }
}