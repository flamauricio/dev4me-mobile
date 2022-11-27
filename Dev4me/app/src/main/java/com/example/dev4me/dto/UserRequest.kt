package com.example.dev4me.dto

import java.time.LocalDate

class UserRequest (
    val nome: String,
    val email: String,
    val dataNasc: LocalDate,
    val descUsuario: String,
    val cep: String
)