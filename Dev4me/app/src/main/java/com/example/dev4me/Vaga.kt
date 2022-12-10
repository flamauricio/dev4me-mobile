package com.example.dev4me

data class Vaga (
    val id: Integer? = null,
    val titulo: String? = null,
    val contrato: String? = null,
    val localizacao: String? = null,
    val faixaSalarialMin: Double? = null,
    val faixaSalarialMax: Double? = null,
    val descricao: String? = null,
    val atividades: String? = null,
    val requisitos: String? = null,
    val level: String? = null,
    val disponivel: Boolean? = null,
    val fkEmpresa: Empresa
)