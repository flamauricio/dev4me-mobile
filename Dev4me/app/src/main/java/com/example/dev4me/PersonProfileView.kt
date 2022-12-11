package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dev4me.databinding.ActivityPersonProfileViewBinding
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonProfileView : AppCompatActivity() {

    private lateinit var binding: ActivityPersonProfileViewBinding

    private var id: Int = 0
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonProfileViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.getIntExtra("id", 0)
        loadUserInfo(id)

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }
    }

    private fun loadUserInfo(id: Int) {
        val userRequest = retrofit.create(
            com.example.dev4me.endpoints.Usuario::class.java
        )

        userRequest.getUsuarioTags(id).enqueue(
            object : Callback<UsuarioTag> {
                override fun onResponse(
                    call: Call<UsuarioTag>,
                    response: Response<UsuarioTag>
                ) {
                    if (response.code() == 200) {

                        val userInfos = response.body()!!
                        MaterialAlertDialogBuilder(this@PersonProfileView)
                            .setMessage(userInfos.toString())
                            .show()

                        userInfos.tags.forEach {
                            val tag = layoutInflater.inflate(R.layout.res_tag, null)
                            tag.findViewById<TextView>(R.id.tagName).text = it.nome

                            binding.usuarioTags.addView(tag)
                        }

                        val id = userInfos.usuario.id
                        val name = userInfos.usuario.nome
                        val localization = userInfos.usuario.cep
                        val description = userInfos.usuario.descUsuario

                        binding.usuarioName.text = name
                        binding.usuarioLocalization.text = localization
                        binding.usuarioDescription.text = description

                    }
                }

                override fun onFailure(call: Call<UsuarioTag>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@PersonProfileView)
                        .setMessage(resources.getString(R.string.api_error))
                        .show()
                }

            }
        )
    }
}