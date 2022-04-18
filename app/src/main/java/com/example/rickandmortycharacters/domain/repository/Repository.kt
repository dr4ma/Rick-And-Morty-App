package com.example.rickandmortycharacters.domain.repository

import com.example.rickandmortycharacters.domain.models.Character
import retrofit2.Response

interface Repository {

    suspend fun getAllCharacters() : Response<Character>

    //suspend fun getSingleCharacter(characterId : String) : Response<Character>
}