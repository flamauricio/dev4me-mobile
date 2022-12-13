package com.example.dev4me.dto

data class UserRequest (
    val id: Integer,
    val nome: String,
    val email: String,
    val descUsuario: String,
    val cep: String,
    val telefone: String
)