package com.example.rickandmortycharacters.data.retrofit

import com.example.rickandmortycharacters.domain.models.retrofit.Character
import com.example.rickandmortycharacters.domain.repository.Repository
import retrofit2.Response

class RepositoryImpl : Repository {

    override suspend fun getAllCharacters() : Response<Character>{
        return RetrofitInstance.apiService.getAllCharacters()
    }

//    override suspend fun getSingleCharacter(characterId : String): Response<Character> {
//        return RetrofitInstance.apiService.getSingleCharacter(characterId)
//    }
}