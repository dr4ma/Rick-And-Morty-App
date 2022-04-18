package com.example.rickandmortycharacters.domain.models

data class Character(val results: MutableList<ResultsItem>?,
                     val info: Info)