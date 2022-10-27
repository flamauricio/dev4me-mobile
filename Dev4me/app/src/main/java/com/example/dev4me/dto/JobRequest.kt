package com.example.dev4me.dto

class JobRequest(
    val companyName: String,
    val jobTitle: String,
    val level: String,
    val localization: String,
    val contract: String,
    val minSalary: Double,
    val maxSalary: Double,
    val salaryRange: String
)