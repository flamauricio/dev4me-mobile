package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityUserMenuBinding
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserMenu : AppCompatActivity() {

    private lateinit var binding: ActivityUserMenuBinding
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val id: Int = prefs.getInt("id", 0)

        if (id != 0) {
            pegarDadosUsuario(id)
        }

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.editPersonalData.setOnClickListener {
            val navigate = Intent(this, UserProfile::class.java)
            startActivity(navigate)
        }

        binding.changePassword.setOnClickListener {
            val navigate = Intent(this, MudarSenha::class.java)
            startActivity(navigate)
        }

        binding.myApplyedJobs.setOnClickListener {
            val navigate = Intent(this, UserCandidacies::class.java)
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

        binding.seta.setOnClickListener {
            startActivity(Intent(this, FeedUser::class.java))
        }
    }

    private fun pegarDadosUsuario(id: Int) {
        val getUserRequest = retrofit.create(
            Usuario::class.java
        )
        getUserRequest.getUsuario(id).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == 200) {
                    plotarDadosNaTela(response.body())
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                MaterialAlertDialogBuilder(this@UserMenu)
                    .setMessage(t.message)
                    .show()
            }
        })
    }

    private fun plotarDadosNaTela(usuario: JsonObject?) {
        val nome: String = usuario?.get("nome").toString()
        val cpf: String = usuario?.get("cpf").toString()
        val telefone: String = usuario?.get("telefone").toString()
        val endereco: String = usuario?.get("endereco").toString()
        val descricao: String = usuario?.get("descUsuario").toString()
        val email: String = usuario?.get("email").toString()

        if (nome != "null") binding.nome.setText(nome.substring(1, nome.length-1))
        if (cpf != "null") binding.cpf.setText(cpf.substring(1, cpf.length-1))
        if (telefone != "null") binding.telefone.setText(telefone.substring(1, telefone.length-1))

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        editor.putString("nome", nome.substring(1, nome.length-1))
        editor.putString("cpf", cpf.substring(1, cpf.length-1))
        editor.putString("telefone", telefone.substring(1, telefone.length-1))
        editor.putString("endereco", endereco.substring(1, endereco.length-1))
        editor.putString("descricao", descricao.substring(1, descricao.length-1))
        editor.putString("email", email.substring(1, email.length-1))
        editor.commit()
    }
}