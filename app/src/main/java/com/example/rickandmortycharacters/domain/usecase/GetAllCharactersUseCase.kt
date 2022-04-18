package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.models.Character
import com.example.rickandmortycharacters.domain.models.ResultsItem
import com.example.rickandmortycharacters.domain.repository.Repository
import retrofit2.Response
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getAllCharacters() : MutableList<ResultsItem>{
        return repository.getAllCharacters().body()?.results!!
    }
}