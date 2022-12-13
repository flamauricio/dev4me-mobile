package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev4me.adapter.JobsAdapter
import com.example.dev4me.databinding.ActivityOpenedCardJobBinding
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenedCardJob : AppCompatActivity() {

    private lateinit var binding: ActivityOpenedCardJobBinding

    private var idVaga: Int = 0
    val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenedCardJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idVaga = intent.getIntExtra("idVaga", 0)
        loadJobInfo(idVaga)

        val idUsuario =getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getInt("id", 0)

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, UserMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.applyButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.alert_title))
                .setMessage(resources.getString(R.string.alert_message))
                .setNegativeButton(resources.getString(R.string.alert_no)) { dialog, which ->
                    // Do nothing
                }
                .setPositiveButton(resources.getString(R.string.alert_yes)) { dialog, which ->
                    // Notify company and add to user candidacies
                    postApplication(idVaga)
                    binding.applyButton.text = resources.getString(R.string.button_apply_now_active)
                    binding.applyButton.setBackgroundColor(resources.getColor(R.color.light_gray))
                    // Disable "apply now" button
                }
                .show()
        }
    }

    private fun postApplication(idVaga: Int){
        retrofit.create(com.example.dev4me.endpoints.CandidatoVaga::class.java)
            .postApplication(idVaga, getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE).getInt("id", 0))
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                    if (response.code() == 201) {
//                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@OpenedCardJob).setMessage(t.message.toString())
                        .show()
                }
            })
    }

    private fun loadJobInfo(idVaga: Int) {
        val vagaRequest = retrofit.create(
            com.example.dev4me.endpoints.Vaga::class.java
        )

        vagaRequest.getVagaById(idVaga).enqueue(
            object : Callback<VagaTag> {
                override fun onResponse(
                    call: Call<VagaTag>,
                    response: Response<VagaTag>
                ) {
                    if (response.code() == 200) {

                        val vagaInfos = response.body()!!

                        vagaInfos.tags.forEach {
                            val tag = layoutInflater.inflate(R.layout.res_tag, null)
                            tag.findViewById<TextView>(R.id.tagName).text = it.nome

                            binding.vagaTags.addView(tag)
                        }

                        val id = vagaInfos.vaga.idVaga
                        val titulo = vagaInfos.vaga.titulo
                        val contrato = vagaInfos.vaga.contrato
                        val localizacao = vagaInfos.vaga.localizacao
                        val faixaSalarialMin = vagaInfos.vaga.faixaSalarialMin
                        val faixaSalarialMax = vagaInfos.vaga.faixaSalarialMax
                        val descricao = vagaInfos.vaga.descricao
                        val atividades = vagaInfos.vaga.atividades
                        val requisitos = vagaInfos.vaga.requisitos
                        val level = vagaInfos.vaga.level
                        val nomeEmpresa = vagaInfos.vaga.fkEmpresa.nome

                        binding.vagaTitle.text = titulo
                        binding.vagaContract.text = contrato
                        binding.vagaLocalization.text = localizacao
                        binding.vagaSalary.text = "R\$ $faixaSalarialMin até $faixaSalarialMax"
                        binding.vagaDescription.text = descricao
                        binding.vagaActivities.text = atividades
                        binding.vagaRequiriments.text = requisitos
                        binding.vagaLevel.text = level
                        binding.vagaCompanyName.text = nomeEmpresa
                    }
                }

                override fun onFailure(call: Call<VagaTag>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@OpenedCardJob)
                        .setMessage(resources.getString(R.string.api_error))
                        .show()
                }

            }
        )
    }
}