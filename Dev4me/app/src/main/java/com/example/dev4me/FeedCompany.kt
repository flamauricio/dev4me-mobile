package com.example.dev4me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dev4me.databinding.ActivityFeedCompanyBinding
import com.example.dev4me.dto.UserRequest
import com.example.dev4me.dto.UsuarioRequest
import com.example.dev4me.endpoints.CEP
import com.example.dev4me.endpoints.Usuario
import com.example.dev4me.endpoints.Vaga
import com.example.dev4me.retrofit.Rest
import com.example.dev4me.retrofit.ViaCEP
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedCompany : AppCompatActivity() {

    private lateinit var binding: ActivityFeedCompanyBinding

    val retrofit = Rest.getInstance()
    val viaCEP = ViaCEP.getInstance()
    var usersList: List<UserRequest> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bringUsersList()

//        binding.personCard1.setOnClickListener {
//            val navigatePersonProfile = Intent(this, PersonProfileView::class.java)
//            startActivity(navigatePersonProfile)
//        }

        binding.profileIcon.setOnClickListener {
            val navigateToProfile = Intent(this, CompanyMenu::class.java)
            startActivity(navigateToProfile)
        }


    }

    private fun bringUsersList() {
        val usuarioRequest = retrofit.create(
            Usuario::class.java
        )

        usuarioRequest.getUsers().enqueue(
            object : Callback<List<UserRequest>> {
                override fun onResponse(
                    call: Call<List<UserRequest>>,
                    response: Response<List<UserRequest>>
                ) {
                    if (response.code() == 200) {
                        usersList = response.body()!!
                        startRecyclerView(usersList)
                    }
                }

                override fun onFailure(call: Call<List<UserRequest>>, t: Throwable) {
                    MaterialAlertDialogBuilder(this@FeedCompany)
                        .setMessage(resources.getString(R.string.api_error))
                        .show()
                }
            }
        )
    }

     fun getCEP(cep: Int) {
        val getCEP = retrofit.create(
            CEP::class.java
        )
         getCEP.getCEP(cep).enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                if (response.code() == 200) {
                    // Utilazar o CEP para mostrar a cidade e estado do usuário
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                MaterialAlertDialogBuilder(this@FeedCompany)
                    .setMessage(t.message)
                    .show()
            }
        })
    }

    private fun startRecyclerView(usersList: List<UserRequest>) {
        val recyclerContainer = binding.recyclerViewFeedCompany
        recyclerContainer.layoutManager = LinearLayoutManager(
            baseContext
        )
        recyclerContainer.adapter = UsersAdapter(
            usersList
        ) { mensagem ->
            Toast.makeText(
                baseContext,
                mensagem,
                Toast.LENGTH_LONG
            ).show()
        }

    }
}

class UsersAdapter(
    private val usersList: List<UserRequest>,
    private val onclick: (mensagem: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    )
            : RecyclerView.ViewHolder {

        val layoutCard = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.res_card_user, parent, false)

        return UserHolder(
            layoutCard,
        )

    }

    inner class UserHolder(
        private val layoutCard: View
    ) : RecyclerView.ViewHolder(layoutCard) {
        fun sync(user: UserRequest) {

            val card = layoutCard
                .findViewById<com.google.android.material.card.MaterialCardView>(
                    R.id.personCard
                )
            val nameUser = layoutCard
                .findViewById<TextView>(R.id.nameUser)
            val localization = layoutCard
                .findViewById<TextView>(R.id.localization)
            val userDescription = layoutCard
                .findViewById<TextView>(R.id.userDescription)

            nameUser.text = user.nome
            localization.text = user.cep
            userDescription.text = user.descUsuario
            card.setOnClickListener {
                // Abre o PersonProfileView
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        (holder as UserHolder).sync(usersList[position])
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}