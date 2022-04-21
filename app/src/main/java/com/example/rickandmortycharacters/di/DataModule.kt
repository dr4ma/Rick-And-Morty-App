package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.data.retrofit.RetrofitRepositoryImpl
import com.example.rickandmortycharacters.domain.repository.RetrofitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRepositoryImpl() : RetrofitRepository{
        return RetrofitRepositoryImpl()
    }
}