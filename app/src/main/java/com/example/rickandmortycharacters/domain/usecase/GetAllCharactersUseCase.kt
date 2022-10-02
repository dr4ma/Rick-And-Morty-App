package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import com.example.rickandmortycharacters.domain.usecase.interfaces.GetAllCharactersInterface
import com.example.rickandmortycharacters.utilits.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAllCharactersUseCase : GetAllCharactersInterface {

    private val emptyList = mutableListOf<ResultsItem>()
    private val retrofitRepository = ServiceLocator.get(RetrofitRepository::class) as RetrofitRepository

    override suspend fun getAllCharacters(page : Int) : Flow<MutableList<ResultsItem>> = flow {
        val request = retrofitRepository.getAllCharacters(page)
        if(request.isSuccessful){
            emit(request.body()?.results ?: emptyList)
        }
    }.flowOn(Dispatchers.IO).distinctUntilChanged()
}