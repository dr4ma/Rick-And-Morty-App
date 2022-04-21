package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val retrofitRepository: RetrofitRepository) {

    suspend fun getAllCharacters() : MutableList<ResultsItem>{
        return retrofitRepository.getAllCharacters().body()?.results!!
    }
}