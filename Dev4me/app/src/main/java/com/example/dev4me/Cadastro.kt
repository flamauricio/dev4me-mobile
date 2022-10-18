package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityCadastroBinding
import com.example.dev4me.dto.EmpresaRequest
import com.example.dev4me.dto.UsuarioRequest
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Cadastro : AppCompatActivity() {

    lateinit var binding: ActivityCadastroBinding
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cadastrarBtn.setOnClickListener {
            showTerms()
        }

        binding.jaPossuoConta.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun showTerms() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.usage_terms))
            .setMessage(resources.getString(R.string.usage_terms_lgpd_text))
            .setNegativeButton(resources.getString(R.string.alert_disagree)) { dialog, which ->
                // Do nothing
            }
            .setPositiveButton(resources.getString(R.string.alert_agree)) { dialog, which ->
                cadastrar()
            }
            .show()
    }

    private fun cadastrar() {
        if (!validarCampos()) {
            return
        }

        val nome: String = binding.nomeEt.text.toString()
        val email: String = binding.emailEt.text.toString()
        val senha: String = binding.passwordEt.text.toString()

        val cadastroRequest = retrofit.create(
            com.example.dev4me.endpoints.Cadastro::class.java
        )

        if (binding.candidatoRb.isChecked) {
            val body: UsuarioRequest = UsuarioRequest(
                nome,
                email,
                senha
            )
            cadastroRequest.postUsuario(body).enqueue(
                object: Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.code() == 203) {
                            MaterialAlertDialogBuilder(this@Cadastro)
                                .setMessage("Email já cadastrado.")
                                .show()
                            return
                        }

                        if (response.code() == 201) {
                            startActivity(Intent(this@Cadastro, FeedUser::class.java))
                            return
                        }

                        MaterialAlertDialogBuilder(this@Cadastro)
                            .setMessage("Dados inválidos.")
                            .show()
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        MaterialAlertDialogBuilder(this@Cadastro)
                            .setMessage(t.message)
                            .show()
                    }
                }
            )
        } else {
            val body: EmpresaRequest = EmpresaRequest(
                nome,
                email,
                senha
            )
            cadastroRequest.postEmpresa(body).enqueue(
                object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.code() == 409) {
                            MaterialAlertDialogBuilder(this@Cadastro)
                                .setMessage("Email já cadastrado.")
                                .show()
                            return
                        }

                        if (response.code() == 201) {
                            startActivity(Intent(this@Cadastro, FeedCompany::class.java))
                            return
                        }

                        MaterialAlertDialogBuilder(this@Cadastro)
                            .setMessage("Dados inválidos.")
                            .show()
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        MaterialAlertDialogBuilder(this@Cadastro)
                            .setMessage(t.message)
                            .show()
                    }
                }
            )
        }
    }

    private fun validarCampos(): Boolean {
        val nome: String? = binding.nomeEt.text.toString()
        val email: String? = binding.emailEt.text.toString()
        val password: String? = binding.passwordEt.text.toString()
        var deuRuim: Boolean = false;

        if (nome.isNullOrBlank()) {
            binding.nomeEt.setError("Campo não pode ser nulo.")
            deuRuim = true;
        }

        if (email.isNullOrBlank()) {
            binding.emailEt.setError("Campo não pode ser nulo.")
            deuRuim = true;
        }

        if (password.isNullOrBlank()) {
            binding.passwordEt.setError("Campo não pode ser nulo.")
            deuRuim = true;
        }

        if (deuRuim) {
            return false
        }

        return true
    }
}