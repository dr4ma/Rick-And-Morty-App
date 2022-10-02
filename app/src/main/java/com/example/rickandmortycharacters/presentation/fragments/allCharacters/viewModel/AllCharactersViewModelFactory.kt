package com.example.rickandmortycharacters.presentation.fragments.allCharacters.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortycharacters.data.retrofit.RetrofitRepositoryImpl
import com.example.rickandmortycharacters.data.room.RoomRepositoryImpl
import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import com.example.rickandmortycharacters.domain.usecase.interfaces.CacheDataInDatabaseInterface
import com.example.rickandmortycharacters.domain.usecase.CacheDataInDatabaseUseCase
import com.example.rickandmortycharacters.domain.usecase.interfaces.GetAllCharactersInterface
import com.example.rickandmortycharacters.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmortycharacters.utilits.ServiceLocator

class AllCharactersViewModelFactory : ViewModelProvider.Factory {

    private lateinit var getAllCharactersUseCase: GetAllCharactersInterface
    private lateinit var cacheDataInDatabaseUseCase: CacheDataInDatabaseInterface

    private fun setInCache(){
        ServiceLocator.set(RetrofitRepository :: class, RetrofitRepositoryImpl::class)
        ServiceLocator.set(GetAllCharactersInterface :: class, GetAllCharactersUseCase::class)
        ServiceLocator.set(DatabaseRepository :: class, RoomRepositoryImpl::class)
        ServiceLocator.set(CacheDataInDatabaseInterface :: class, CacheDataInDatabaseUseCase::class)
    }

    private fun getLocator(){
        getAllCharactersUseCase = ServiceLocator.get(GetAllCharactersInterface::class) as GetAllCharactersInterface
        cacheDataInDatabaseUseCase =  ServiceLocator.get(CacheDataInDatabaseInterface::class) as CacheDataInDatabaseInterface
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        setInCache()
        getLocator()
        return AllCharactersViewModel(
            getAllCharactersUseCase = getAllCharactersUseCase,
            cacheDataInDatabaseUseCase = cacheDataInDatabaseUseCase
        ) as T
    }
}