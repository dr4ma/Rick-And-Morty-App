package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.data.room.DatabaseRepository
import com.example.rickandmortycharacters.domain.repository.Repository
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
    fun provideGetAllCharacters(repository: Repository): GetAllCharactersUseCase{
        return GetAllCharactersUseCase(repository = repository)
    }

    @Provides
    fun provideCache(databaseRepository: DatabaseRepository): CacheDataInDatabaseUseCase{
        return CacheDataInDatabaseUseCase(databaseRepository = databaseRepository)
    }

}