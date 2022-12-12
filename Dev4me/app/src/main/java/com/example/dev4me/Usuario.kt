package com.example.dev4me

import java.time.LocalDate


class Usuario (
    private val id: Int?,
    private val nome: String?,
    private val email: String?,
    private val senha: String?,
    private val dataNasc: LocalDate?,
    private val descUsuario: String?,
    private val cpf: String?,
    private val telefone: String?,
    private val cep: String?,
    private val endereco: String?
)