package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dev4me.databinding.ActivityCompanyPostedJobsBinding
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyUsersApplied : AppCompatActivity() {

    private lateinit var binding: ActivityCompanyPostedJobsBinding
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyPostedJobsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getVagas()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.nomeEmpresa.text = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("nome", "")
        binding.cnpj.text = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getString("cnpj", "")
    }


    private fun getVagas() {
        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        retrofit.create(com.example.dev4me.endpoints.CandidatoVaga::class.java).getCandidatosVagas(getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getInt("id", 0))
            .enqueue(object : Callback<List<CandidatoVaga>> {
                override fun onResponse(
                    call: Call<List<CandidatoVaga>>,
                    response: Response<List<CandidatoVaga>>
                ) {
                    if (response.code() == 200) {
                        val list: List<CandidatoVaga> = response.body()!!
                        list.forEach {
                            val card = layoutInflater.inflate(R.layout.res_card_applied_user, null)
                            card.findViewById<TextView>(R.id.cardAppliedUserName).text = it.fkUsuario.nome
                            card.findViewById<TextView>(R.id.cardAppliedJobTitle).text = it.fkVaga.titulo
                            card.findViewById<TextView>(R.id.cardAppliedUserEmail).text = it.fkUsuario.email
                            card.findViewById<TextView>(R.id.cardAppliedUserPhoneNumber).text = it.fkUsuario.telefone

                            binding.vagas.addView(card)
                        }
                    }
                }

                override fun onFailure(call: Call<List<CandidatoVaga>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

    }
}