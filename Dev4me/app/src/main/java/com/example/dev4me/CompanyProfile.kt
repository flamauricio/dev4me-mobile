package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityCompanyProfileBinding
import com.example.dev4me.dto.PatchEmpresaRequest
import com.example.dev4me.endpoints.Empresa
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CompanyProfile : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyProfileBinding
    private val retrofit = Rest.getInstance()
    private var id: Int? = null
    private var nome: String? = null
    private var email: String? = null
    private var cnpj: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plotarInformacoesNaTela()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.salvar.setOnClickListener {
            salvarDados()
        }
    }

    fun plotarInformacoesNaTela() {
        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        id = prefs.getInt("id", 0)

        nome = prefs.getString("nome", null)
        email = prefs.getString("email", null)
        cnpj = prefs.getString("cnpj", null)

        if (!validarAtributos()) {
            return
        }

        if (nome != "null") binding.nome.setText(nome)
        if (email != "null") binding.email.setText(email)
        if (cnpj != "null") binding.cnpj.setText(cnpj)
    }

    fun validarAtributos(): Boolean {
        if (id == 0) {
            MaterialAlertDialogBuilder(this@CompanyProfile)
                .setMessage("Erro, ID não reconhecido.")
                .show()
            return false
        }

        return true
    }

    fun salvarDados() {
        val patchDados = retrofit.create(
            Empresa::class.java
        )

        patchDados.patchDados(PatchEmpresaRequest(
            id,
            binding.nome.text.toString(),
            binding.email.text.toString(),
            null,
            binding.cnpj.text.toString()
        )).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.code() == 200) {
                    MaterialAlertDialogBuilder(this@CompanyProfile)
                        .setMessage("Dados alterados com sucesso!")
                        .show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                MaterialAlertDialogBuilder(this@CompanyProfile)
                    .setMessage(t.message)
                    .show()
            }
        })
    }
}