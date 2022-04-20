package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.repository.Repository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getAllCharacters() : MutableList<ResultsItem>{
        return repository.getAllCharacters().body()?.results!!
    }
}