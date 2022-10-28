package com.example.dev4me

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityMudarSenhaBinding
import com.example.dev4me.dto.SenhaRequest
import com.example.dev4me.endpoints.Empresa
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Response

class MudarSenha : AppCompatActivity() {

    private lateinit var binding: ActivityMudarSenhaBinding
    private val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMudarSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.salvar.setOnClickListener {
            alterarSenha()
        }
    }

    private fun alterarSenha() {
        val senhaAtual = binding.senhaAtual
        val novaSenha = binding.novaSenha
        val novaSenhaConfirmada = binding.confirmeNovaSenha

        if (!validarCampos()) {
            return
        }

        if (!validarSeSenhasConferem()) {
            return
        }

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val id: Int = prefs.getInt("id", 0)

        if (id == 0) {
            MaterialAlertDialogBuilder(this@MudarSenha)
                .setMessage("Erro... Favor, refazer o login. ID não encontrado.")
                .show()
            return
        }

        val tipoUsuario = prefs.getString("tipoUsuario", "")

        if (tipoUsuario == "") {
            MaterialAlertDialogBuilder(this@MudarSenha)
                .setMessage("Erro... Favor, refazer o login. Tipo de usuário não encontrado.")
                .show()
            return
        }

        if (tipoUsuario == "candidato") {
            val patchSenhaUsuario = retrofit.create(
                Usuario::class.java
            )

            val senhaRequest = SenhaRequest(
                id,
                senhaAtual.text.toString(),
                novaSenha.text.toString()
            )

            patchSenhaUsuario.patchSenhaUsuario(id, senhaRequest).enqueue(object : retrofit2.Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.code() == 200) {
                        MaterialAlertDialogBuilder(this@MudarSenha)
                            .setMessage("Senha atualizada com sucesso!")
                            .show()
                        return
                    }

                    if (response.code() == 404) {
                        MaterialAlertDialogBuilder(this@MudarSenha)
                            .setMessage("Senha atual não confere")
                            .show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@MudarSenha)
                        .setMessage(t.message)
                        .show()
                }
            })

            return
        }

        val patchSenhaEmpresa = retrofit.create(
            Empresa::class.java
        )

        val senhaRequest = SenhaRequest(
            id,
            senhaAtual.text.toString(),
            novaSenha.text.toString()
        )

        patchSenhaEmpresa.patchSenhaEmpresa(id, senhaRequest)
            .enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.code() == 200) {
                    MaterialAlertDialogBuilder(this@MudarSenha)
                        .setMessage("Senha atualizada com sucesso!")
                        .show()
                    return
                }

                if (response.code() == 404) {
                    MaterialAlertDialogBuilder(this@MudarSenha)
                        .setMessage("Senha atual não confere")
                        .show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                MaterialAlertDialogBuilder(this@MudarSenha)
                    .setMessage(t.message)
                    .show()
            }
        })
    }

    private fun validarCampos(): Boolean {
        val senhaAtual = binding.senhaAtual
        val novaSenha = binding.novaSenha
        val novaSenhaConfirmada = binding.confirmeNovaSenha
        var deuBom = true

        if (validarCampo(senhaAtual.text?.toString())) {
            senhaAtual.error = "Este campo não pode estar vazio."
            deuBom = false
        }

        if (validarCampo(novaSenha.text?.toString())) {
            novaSenha.error = "Este campo não pode estar vazio."
            deuBom = false
        }

        if (validarCampo(novaSenhaConfirmada.text?.toString())) {
            novaSenhaConfirmada.error = "Este campo não pode estar vazio."
            deuBom = false
        }

        if (deuBom) {
            return true
        }

        return false
    }

    private fun validarCampo(campo: String?): Boolean {
        if (campo?.length!! > 0) {
            return false
        }

        return true
    }

    private fun validarSeSenhasConferem(): Boolean {
        val novaSenha = binding.novaSenha
        val novaSenhaConfirmada = binding.confirmeNovaSenha

        if (!novaSenha.text?.toString().equals(novaSenhaConfirmada.text?.toString())) {
            novaSenha.error = "Senhas não conferem!"
            novaSenhaConfirmada.error = "Senhas não conferem!"
            return false
        }

        return true
    }
}