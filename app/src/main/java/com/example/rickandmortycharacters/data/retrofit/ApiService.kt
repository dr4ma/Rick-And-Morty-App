package com.example.rickandmortycharacters.data.retrofit

import com.example.rickandmortycharacters.domain.models.retrofit.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page: Int):Response<Character>

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") CharacterId: String) : Response<Character>
}