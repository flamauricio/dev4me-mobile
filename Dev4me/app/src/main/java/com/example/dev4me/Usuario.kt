package com.example.dev4me

import java.time.LocalDate

data class Usuario(

    val id: Integer,

    val nome: String,

    val email: String,

    val senha: String,

    val dataNasc: LocalDate,

    val descUsuario: String,

    val cpf: String,

    val telefone: String,

    val cep: String,

    val endereco: String
)