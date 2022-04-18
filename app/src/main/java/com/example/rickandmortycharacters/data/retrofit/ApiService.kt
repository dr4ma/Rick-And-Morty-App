package com.example.rickandmortycharacters.data.retrofit

import com.example.rickandmortycharacters.domain.models.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters():Response<Character>

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") CharacterId: String) : Response<Character>
}