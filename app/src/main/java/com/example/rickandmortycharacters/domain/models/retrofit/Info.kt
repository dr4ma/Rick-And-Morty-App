package com.example.rickandmortycharacters.domain.models.retrofit

data class Info(val next: String = "",
                val pages: Int = 0,
                val prev: String? = null,
                val count: Int = 0)