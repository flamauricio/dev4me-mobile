package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.adapter.JobsAdapter
import com.example.dev4me.adapter.UserAdapter
import com.example.dev4me.databinding.ActivityFeedCompanyBinding
import com.example.dev4me.dto.UserRequest
import com.example.dev4me.dto.UsuarioRequest
import com.example.dev4me.endpoints.CEP
import com.example.dev4me.endpoints.Tag
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.retrofit.Rest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedCompany : AppCompatActivity() {

    private lateinit var binding: ActivityFeedCompanyBinding

    val retrofit = Rest.getInstance()
//    val viaCEP = ViaCEP.getInstance()
    var usersList: List<com.example.dev4me.Usuario> = listOf()
    var tagsList: List<com.example.dev4me.Tag> = listOf()
    val selectedTags: MutableList<com.example.dev4me.Tag> = ArrayList<com.example.dev4me.Tag>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bringUsersList()
        getTags()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }

        binding.searchButton.setOnClickListener {
            filtrar()
        }
    }

    private fun filtrar() {
        if (selectedTags.isEmpty()) {
            bringUsersList()
            return
        }

        retrofit.create(com.example.dev4me.endpoints.Usuario::class.java).getUsersByTags(selectedTags)
            .enqueue(object : Callback<List<UserRequest>> {
                override fun onResponse(
                    call: Call<List<UserRequest>>,
                    response: Response<List<UserRequest>>
                ) {
                    if (response.code() == 200) {
                        usersList = response.body()!!
                        val layoutManager = LinearLayoutManager(this@FeedCompany)
                        binding.recyclerViewFeedCompany.layoutManager = layoutManager
                        val adapter = UserAdapter(usersList, this@FeedCompany)
                        binding.recyclerViewFeedCompany.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<UserRequest>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedCompany)
                        .setMessage(t.message)
                        .show()
                }
            })
    }

    private fun bringUsersList() {
        val usuarioRequest = retrofit.create(
            com.example.dev4me.endpoints.Usuario::class.java
        )

        usuarioRequest.getUsers().enqueue(
            object : Callback<List<com.example.dev4me.Usuario>> {
                override fun onResponse(
                    call: Call<List<com.example.dev4me.Usuario>>,
                    response: Response<List<com.example.dev4me.Usuario>>
                ) {
                    if (response.code() == 200) {
                        usersList = response.body()!!
                        val layoutManager = LinearLayoutManager(this@FeedCompany)
                        binding.recyclerViewFeedCompany.layoutManager = layoutManager
                        val adapter = UserAdapter(usersList, this@FeedCompany)
                        binding.recyclerViewFeedCompany.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<com.example.dev4me.Usuario>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedCompany)
                        .setMessage(resources.getString(R.string.api_error) + t.message)
                        .show()
                }
            }
        )
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

                        binding.tagsFiltro.addView(tag)
                    }
                }
            }

            override fun onFailure(call: Call<List<com.example.dev4me.Tag>>, t: Throwable) {
                MaterialAlertDialogBuilder(this@FeedCompany).setMessage(t.message.toString())
                    .show()
            }
        })
    }
}