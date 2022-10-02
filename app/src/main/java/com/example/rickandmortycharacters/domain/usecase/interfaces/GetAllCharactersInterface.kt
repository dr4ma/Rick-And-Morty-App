package com.example.rickandmortycharacters.domain.usecase.interfaces

import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import com.example.rickandmortycharacters.utilits.ServiceLocator
import kotlinx.coroutines.flow.Flow

interface GetAllCharactersInterface : ServiceLocator.Service{

    suspend fun getAllCharacters(page : Int) : Flow<MutableList<ResultsItem>>
}