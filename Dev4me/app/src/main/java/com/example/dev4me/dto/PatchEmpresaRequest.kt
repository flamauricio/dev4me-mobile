package com.example.dev4me.dto

data class PatchEmpresaRequest (
    var idEmpresa: Int? = null,
    var nome: String? = null,
    var email: String? = null,
    var senha: String? = null,
    var cnpj: String? = null
)