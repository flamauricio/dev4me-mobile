package com.example.dev4me.dto

class JobRequest(
    val idVaga: Int,
    val titulo: String,
    val contrato: String,
    val localizacao: String,
    val faixaSalarialMin: Double,
    val faixaSalarialMax: Double,
    val descricao: String,
    val atividades: String,
    val requisitos: String,
    val level: String,
    val disponivel: Boolean,
    val fkEmpresa: Int,
    val nomeEmpresa: String
)