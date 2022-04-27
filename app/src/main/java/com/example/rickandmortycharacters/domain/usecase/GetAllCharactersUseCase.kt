package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val retrofitRepository: RetrofitRepository) {

    private val test = mutableListOf<ResultsItem>()

    suspend fun getAllCharacters(page : Int) : MutableList<ResultsItem>? {
        val request = retrofitRepository.getAllCharacters(page)

        return if(!request.isSuccessful){
            test
        } else{
            retrofitRepository.getAllCharacters(page).body()?.results
        }
    }
}