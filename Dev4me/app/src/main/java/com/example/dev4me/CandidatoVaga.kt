package com.example.dev4me

data class CandidatoVaga (
    val id: Integer,
    val fkUsuario: Usuario,
    val fkVaga: Vaga
)