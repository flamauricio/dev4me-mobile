package com.example.dev4me

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.example.dev4me.databinding.ActivityCadastroVagaBinding
import com.example.dev4me.dto.TagVagaRequest
import com.example.dev4me.endpoints.Tag
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CadastroVaga : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroVagaBinding
    val retrofit = Rest.getInstance()
    var titulo: String? = null
    var localizacao: String? = null
    var tipoContrato: String? = null
    var levelVaga: String? = null
    var tagsList: List<com.example.dev4me.Tag> = listOf()
    val selectedTags: MutableList<com.example.dev4me.Tag> = ArrayList<com.example.dev4me.Tag>()
    var valorMinimo: Int? = null
    var valorMaximo: Int? = null
    var descricao: String? = null
    var atividades: String? = null
    var requisitos: String? = null

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

        binding.buttonPublicar.setOnClickListener {
            publicarVaga()
        }
    }

    private fun getTags() {
        val getTagsRequest = retrofit.create(Tag::class.java)
        getTagsRequest.getTags().enqueue(object : Callback<List<com.example.dev4me.Tag>> {
            override fun onResponse(
                call: Call<List<com.example.dev4me.Tag>>,
                response: Response<List<com.example.dev4me.Tag>>
            ) {
                if (response.code() == 200) {
                    tagsList = response.body()!!
                    tagsList.forEach {
                        val tag = layoutInflater.inflate(R.layout.res_tag, null)
                        tag.findViewById<TextView>(R.id.tagName).text = it.nome

                        tag.setOnClickListener { v ->
                            if (!selectedTags.contains(it)) {
                                selectedTags.add(it)
                                tag.findViewById<TextView>(R.id.tagName).setTextColor(getResources().getColor(R.color.purple_200))
                            } else {
                                selectedTags.remove(it)
                                tag.findViewById<TextView>(R.id.tagName).setTextColor(getResources().getColor(R.color.white))
                            }
                        }

                        binding.flexboxTags.addView(tag)
                    }
                }
            }

            override fun onFailure(call: Call<List<com.example.dev4me.Tag>>, t: Throwable) {
                MaterialAlertDialogBuilder(this@CadastroVaga).setMessage(t.message.toString())
                    .show()
            }
        })
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

        valorMinimo = binding.valorMinimo.text.toString().toInt()
        valorMaximo = binding.valorMaximo.text.toString().toInt()

        binding.passoDois.visibility = View.GONE
        binding.passoTres.visibility = View.VISIBLE
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

        if (!deuBom) {
            return false
        }

        if (selectedTags.size < 2) {
            MaterialAlertDialogBuilder(this@CadastroVaga).setMessage(
                "Selecione pelo menos 2 tags. ${selectedTags.size}"
            ).show()
            deuBom = false
        }

        if (selectedTags.size > 8) {
            MaterialAlertDialogBuilder(this@CadastroVaga).setMessage(
                "O máximo de tags por vaga 8 ${selectedTags.size}"
            ).show()
            deuBom = false
        }

        return deuBom
    }

    fun publicarVaga() {
        if (!validarPassoTres()) {
            return
        }

        descricao = binding.breveDesc.text?.toString()
        atividades = binding.atividades.text?.toString()
        requisitos = binding.requisitos.text?.toString()

        val prefs: SharedPreferences = getSharedPreferences("chaveGeral-Xml", MODE_PRIVATE)
        var id = prefs.getInt("id", 0)

        if (id == 0) {
            MaterialAlertDialogBuilder(this@CadastroVaga).setMessage(
                "Por favor, relogue na aplicação"
            ).show()
            return
        }

        val vagaRequest = retrofit.create(
            com.example.dev4me.endpoints.Vaga::class.java
        )

        val vaga = Vaga(
            null,
            titulo,
            tipoContrato,
            localizacao,
            valorMinimo?.toDouble(),
            valorMaximo?.toDouble(),
            descricao,
            atividades,
            requisitos,
            levelVaga,
            true,
            Empresa(id, null, null, null, null)
        )

        vagaRequest.postVaga(vaga).enqueue(
            object : Callback<Vaga> {
                override fun onResponse(call: Call<Vaga>, response: Response<Vaga>) {
                    if (response.code() == 201) {
                        val vaga: Vaga? = response.body()
                        salvarTags(vaga)
                    }
                }

                override fun onFailure(call: Call<Vaga>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@CadastroVaga).setMessage(t.message)
                        .show()
                }
            }
        )
    }

    private fun validarPassoTres(): Boolean {
        var deuBom = true

        if (binding.breveDesc.text?.toString().isNullOrBlank()) {
            binding.breveDesc.setError("Este campo não pode ser vazio.")
            deuBom = false
        }

        if (binding.atividades.text?.toString().isNullOrBlank()) {
            binding.atividades.setError("Este campo não pode ser vazio.")
            deuBom = false
        }

        if (binding.requisitos.text?.toString().isNullOrBlank()) {
            binding.requisitos.setError("Este campo não pode ser vazio.")
            deuBom = false
        }

        return deuBom
    }

    fun salvarTags(vaga: Vaga?) {
        retrofit.create(
            com.example.dev4me.endpoints.Tag::class.java
        ).postTags(
            TagVagaRequest(
                selectedTags
            ),
            vaga?.id
        ).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                MaterialAlertDialogBuilder(this@CadastroVaga).setMessage("Vaga criada com sucesso!")
                    .show()
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                MaterialAlertDialogBuilder(this@CadastroVaga).setMessage(t.message)
                    .show()
            }
        })
    }
}