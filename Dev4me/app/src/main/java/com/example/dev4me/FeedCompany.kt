package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dev4me.adapter.UserAdapter
import com.example.dev4me.databinding.ActivityFeedCompanyBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bringUsersList()

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }


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

//     fun getCEP(cep: Int) {
//        val getCEP = retrofit.create(
//            CEP::class.java
//        )
//         getCEP.getCEP(cep).enqueue(object : Callback<JsonObject> {
//            override fun onResponse(
//                call: Call<JsonObject>,
//                response: Response<JsonObject>
//            ) {
//                if (response.code() == 200) {
//                    // Utilazar o CEP para mostrar a cidade e estado do usuário
//                }
//            }
//
//            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                MaterialAlertDialogBuilder(this@FeedCompany)
//                    .setMessage(t.message)
//                    .show()
//            }
//        })
//    }
}