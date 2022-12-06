package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dev4me.databinding.ActivityCadastroVagaBinding
import com.example.dev4me.endpoints.Tag
import retrofit2.Callback
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class CadastroVaga : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroVagaBinding
    val retrofit = Rest.getInstance()
    var titulo: String? = null
    var localizacao: String? = null
    var tipoContrato: String? = null
    var levelVaga: String? = null
    val tagList: MutableList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroVagaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTags()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.buttonAvancarUm.setOnClickListener {
            avancarPassoDois()
        }

        binding.buttonAvancarDois.setOnClickListener {
            avancarPassoTres()
        }
    }

    private fun getTags() {
        val getTagsRequest = retrofit.create(Tag::class.java)
        getTagsRequest.getTags().enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    MaterialAlertDialogBuilder(this@CadastroVaga)
                        .setMessage(response.body().toString() )
                        .show()
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }

    private fun avancarPassoDois() {
        if (!validarPassoUm()) {
            return
        }

        titulo = binding.tituloVaga.text.toString()
        localizacao = binding.localizacao.text.toString()
        tipoContrato = obterTipoContrato()
        levelVaga = obterLevelVaga()

        binding.passoUm.visibility = View.GONE
        binding.passoDois.visibility = View.VISIBLE
    }

    private fun validarPassoUm(): Boolean {
        val titulo = binding.tituloVaga
        val localizacao = binding.localizacao
        var deuBom: Boolean = true

        if (titulo.text?.toString().isNullOrBlank()) {
            titulo.error = "Este campo não pode ser vazio."
            deuBom = false
        }

        if (localizacao.text?.toString().isNullOrBlank()) {
            localizacao.error = "Este campo não pode ser vazio."
            deuBom = false
        }

        return deuBom
    }

    private fun obterTipoContrato(): String {
        if (binding.remoto.isChecked) return "remoto"
        if (binding.presencial.isChecked) return "presencial"
        return "hibrido"
    }

    private fun obterLevelVaga(): String {
        if (binding.junior.isChecked) return "junior"
        if (binding.pleno.isChecked) return "pleno"
        return "senior"
    }

    private fun avancarPassoTres() {
        if (!validarPassoDois()) {
            return
        }


    }

    private fun validarPassoDois(): Boolean {
        val valorMinimo = binding.valorMinimo
        val valorMaximo = binding.valorMaximo
        var deuBom = true

        if (valorMinimo.text?.toString().isNullOrBlank()) {
            valorMinimo.error = "Este campo não pode ser vazio."
            deuBom = false
        }

        if (valorMaximo.text?.toString().isNullOrBlank()) {
            valorMaximo.error = "Este campo não pode ser vazio."
            deuBom = false
        }

        return deuBom
    }
}