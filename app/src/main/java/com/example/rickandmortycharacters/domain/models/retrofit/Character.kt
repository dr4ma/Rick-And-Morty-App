package com.example.rickandmortycharacters.domain.models.retrofit

data class Character(val results: MutableList<ResultsItem>?,
                     val info: Info
)