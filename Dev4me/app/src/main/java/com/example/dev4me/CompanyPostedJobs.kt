package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dev4me.databinding.ActivityCompanyPostedJobsBinding
import com.example.dev4me.retrofit.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyPostedJobs : AppCompatActivity() {

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
        retrofit.create(com.example.dev4me.endpoints.Vaga::class.java).getAllVagasById(prefs.getInt("id", 0))
            .enqueue(object : Callback<List<Vaga>> {
                override fun onResponse(call: Call<List<Vaga>>, response: Response<List<Vaga>>) {
                    if (response.code() == 200) {
                        val list: List<Vaga> = response.body()!!
                        list.forEach {
                            val card = layoutInflater.inflate(R.layout.res_company_jobs, null)
                            card.findViewById<TextView>(R.id.titulozada).text = it.titulo
                            card.findViewById<TextView>(R.id.tipoContrato).text = it.contrato

//                            card.setOnClickListener { v ->
//                                start
//                            }

                            binding.vagas.addView(card)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Vaga>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

    }
}