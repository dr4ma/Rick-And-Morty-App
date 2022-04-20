package com.example.rickandmortycharacters.di

import com.example.rickandmortycharacters.data.retrofit.RepositoryImpl
import com.example.rickandmortycharacters.data.room.DatabaseRepository
import com.example.rickandmortycharacters.data.room.RoomDatabase
import com.example.rickandmortycharacters.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideRepositoryImpl() : Repository{
        return RepositoryImpl()
    }
}