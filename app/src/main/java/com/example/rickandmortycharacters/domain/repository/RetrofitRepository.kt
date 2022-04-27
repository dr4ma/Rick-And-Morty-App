package com.example.rickandmortycharacters.domain.repository

import com.example.rickandmortycharacters.domain.models.retrofit.Character
import retrofit2.Response

interface RetrofitRepository {

    suspend fun getAllCharacters(page : Int) : Response<Character>
    //suspend fun getSingleCharacter(characterId : String) : Response<Character>
}