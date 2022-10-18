package com.example.dev4me

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.dev4me.databinding.ActivityMainBinding
import com.example.dev4me.dto.LoginRequest
import com.example.dev4me.endpoints.Login
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        if (prefs.getInt("id", 0) != 0) {
            val tipoUsuario = prefs.getString("tipoUsuario", "");

            if (tipoUsuario == "candidato") {
                startActivity(Intent(this, FeedUser::class.java))
                return
            }

            if (tipoUsuario == "contratante") {
                startActivity(Intent(this, FeedCompany::class.java))
            }
        }

        binding.entrarBtn.setOnClickListener {
            login()
        }

        binding.criarConta.setOnClickListener {
            startActivity(Intent(this, Cadastro::class.java))
        }

    }

    private fun login() {
        if (!validarCampos()) {
            return
        }

        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val body = LoginRequest(null ,email, password)

        val loginRequest = retrofit.create(
            Login::class.java
        )

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        if (binding.candidatoRb.isChecked) {
            loginRequest.logarUsuario(body).enqueue(
                object : Callback<Int> {
                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        if (response.code() == 200) {
                            editor.putString("tipoUsuario", "candidato")
                            editor.putInt("id", response.body().toString().toInt())
                            editor.commit()

                            startActivity(Intent(this@MainActivity, FeedUser::class.java))
                            return
                        }

                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setMessage("Email e/ou senha inválidos.")
                            .show()
                    }
                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setMessage(t.message)
                            .show()
                    }
                }
            )

            return
        }

        loginRequest.logarEmpresa(body).enqueue(
            object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {

                    if (response.code() == 200) {
                        editor.putString("tipoUsuario", "contratante")
                        editor.putInt("id", response.body().toString().toInt())
                        editor.commit()

                        startActivity(Intent(this@MainActivity, FeedCompany::class.java))
                        return
                    }

                    MaterialAlertDialogBuilder(this@MainActivity)
                        .setMessage("Email/senha inválidos.")
                        .show()
                }
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@MainActivity)
                        .setMessage(t.message)
                        .show()
                }
            }
        )
    }

    private fun validarCampos(): Boolean {
        val email: String? = binding.emailEt.text.toString()
        val password: String? = binding.passwordEt.text.toString()
        var deuRuim: Boolean = false;

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