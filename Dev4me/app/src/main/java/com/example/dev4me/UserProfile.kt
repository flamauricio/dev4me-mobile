package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityUserProfileBinding
import com.example.dev4me.endpoints.Empresa
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfile : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private val retrofit = Rest.getInstance()
    var id: Int? = null
    var nome: String? = null
    var cpf: String? = null
    var endereco: String? = null
    var descricao: String? = null
    var telefone: String? = null
    var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getInt("id", 0)
        nome = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("nome", null)
        cpf = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("cpf", null)
        endereco = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("endereco", null)
        descricao = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("descricao", null)
        telefone = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("telefone", null)
        email = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("email", null)


        plotarDadosNaTela()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.salvar.setOnClickListener {
            salvarDados()
        }
    }

    private fun plotarDadosNaTela() {
        if (nome != "ul" ) binding.nome.setText(nome)
        if (cpf != "ul") binding.cpf.setText(cpf)
        if (endereco != "ul") binding.localidade.setText(endereco)
        if (descricao != "ul") binding.descricao.setText(descricao)
        if (telefone != "ul") binding.telefone.setText(telefone)
    }

    private fun salvarDados() {
        nome = binding.nome.text?.toString()
        descricao = binding.descricao.text?.toString()
        cpf = binding.cpf.text?.toString()
        telefone = binding.telefone.text?.toString()
        endereco = binding.localidade.text?.toString()

        val patchDados = retrofit.create(
            com.example.dev4me.endpoints.Usuario::class.java
        )
        patchDados.patchUsuario(Usuario(
            id,
            nome,
            email,
            null,
            null,
            descricao,
            cpf,
            telefone,
            null,
            endereco
        )).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                MaterialAlertDialogBuilder(this@UserProfile)
                    .setMessage("Dados alterados com sucesso!")
                    .show()
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                MaterialAlertDialogBuilder(this@UserProfile)
                    .setMessage(t.message)
                    .show()
            }
        })
    }
}