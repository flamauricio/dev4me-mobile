package com.example.dev4me

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dev4me.databinding.ActivityDesativarContaBinding
import com.example.dev4me.endpoints.Empresa
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DesativarConta : AppCompatActivity() {

    private lateinit var binding: ActivityDesativarContaBinding
    private var retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDesativarContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.desativarButton.setOnClickListener {
            desativarConta()
        }
    }

    private fun desativarConta() {

        if (!validarCampos()) {
            return
        }

        if (!verificarConfirmacao()) {
            return
        }

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val tipoUsuario = prefs.getString("tipoUsuario", "")

        if (tipoUsuario == "") {
            MaterialAlertDialogBuilder(this@DesativarConta)
                .setMessage("Erro... Favor, refazer o login. Tipo de usuário não encontrado.")
                .show()
            return
        }

        if (tipoUsuario == "candidato") {
            val desativarRequest = retrofit.create(
                Usuario::class.java
            )
        }

        val desativarRequest = retrofit.create(
            Empresa::class.java
        )

        // desenvolver depois quando back estiver pronto
    }

    fun validarCampos(): Boolean {
        val senha = binding.senhaAtual
        val confirmacao = binding.confirmInput
        var deuBom = true

        if (!validarCampo(senha.text?.toString())) {
            senha.error = "Este campo não pode ser vazio."
            deuBom = false
        }

        if (!validarCampo(confirmacao.text?.toString())) {
            confirmacao.error = "Digite exatamente \"Deletar\" para continuar."
            deuBom = false
        }

        if (!deuBom) {
            return false
        }

        return true
    }

    private fun validarCampo(campo: String?): Boolean {
        if (campo.isNullOrBlank()) {
            return false
        }

        return true
    }

    private fun verificarConfirmacao(): Boolean {
        val confirmacao = binding.confirmInput

        if (!confirmacao.text.toString().equals("DELETAR")) {
            confirmacao.error = "Digite exatamente \"Deletar\" para continuar."
            return false
        }

        return true
    }
}