package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dev4me.databinding.ActivityUserProfileBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
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

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                MaterialAlertDialogBuilder(this@UserProfile)
                    .setMessage(t.message)
                    .show()
            }
        })
    }

    private fun plotarDadosNaTela(usuario: JsonObject?) {
        val nome: String = usuario?.get("nome").toString()
        val idade: String = usuario?.get("idade").toString()
        val localidade: String = usuario?.get("localidade").toString()

//        binding.nome.h = nome.substring(1, nome.length-1)
//        binding.idade.text = idade.substring(1, idade.length-1)
//        binding.localidade.text = localidade.substring(1, localidade.length-1)
    }
}