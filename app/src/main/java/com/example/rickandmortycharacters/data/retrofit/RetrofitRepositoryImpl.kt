package com.example.rickandmortycharacters.data.retrofit

import com.example.rickandmortycharacters.domain.models.retrofit.Character
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import retrofit2.Response

class RetrofitRepositoryImpl : RetrofitRepository {

    override suspend fun getAllCharacters() : Response<Character>{
        return RetrofitInstance.apiService.getAllCharacters()
    }

//    override suspend fun getSingleCharacter(characterId : String): Response<Character> {
//        return RetrofitInstance.apiService.getSingleCharacter(characterId)
//    }
}