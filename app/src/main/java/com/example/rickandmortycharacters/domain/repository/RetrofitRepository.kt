package com.example.rickandmortycharacters.domain.repository

import com.example.rickandmortycharacters.domain.models.retrofit.Character
import com.example.rickandmortycharacters.utilits.ServiceLocator
import retrofit2.Response

interface RetrofitRepository : ServiceLocator.Service{

    suspend fun getAllCharacters(page : Int) : Response<Character>
    //suspend fun getSingleCharacter(characterId : String) : Response<Character>
}