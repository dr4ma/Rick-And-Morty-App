package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import com.example.rickandmortycharacters.domain.usecase.CacheDataInDatabaseUseCase
import com.example.rickandmortycharacters.domain.usecase.GetAllCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllCharacters(retrofitRepository: RetrofitRepository): GetAllCharactersUseCase{
        return GetAllCharactersUseCase(retrofitRepository = retrofitRepository)
    }

    @Provides
    fun provideCache(databaseRepository: DatabaseRepository): CacheDataInDatabaseUseCase{
        return CacheDataInDatabaseUseCase(databaseRepository = databaseRepository)
    }

}