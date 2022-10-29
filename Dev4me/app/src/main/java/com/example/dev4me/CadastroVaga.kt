package com.example.dev4me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.dev4me.databinding.ActivityCadastroVagaBinding

class CadastroVaga : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroVagaBinding
    var titulo: String? = null
    var localizacao: String? = null
    var tipoContrato: String? = null
    var levelVaga: String? = null
    val tagList: MutableList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroVagaBinding.inflate(layoutInflater)
        setContentView(binding.root)

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