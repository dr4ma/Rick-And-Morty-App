package com.example.rickandmortycharacters.domain.usecase

import android.util.Log
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import com.example.rickandmortycharacters.utilits.TAG
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val retrofitRepository: RetrofitRepository) {

    private val emptyList = mutableListOf<ResultsItem>()

    suspend fun getAllCharacters(page : Int) : MutableList<ResultsItem>? {
        val request = retrofitRepository.getAllCharacters(page)
        return if(!request.isSuccessful){
            emptyList
        } else{
            retrofitRepository.getAllCharacters(page).body()?.results
        }
    }
}