package com.example.rickandmortycharacters.di

import android.app.Application
import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import com.example.rickandmortycharacters.data.room.RoomDao
import com.example.rickandmortycharacters.data.room.RoomDatabase
import com.example.rickandmortycharacters.data.room.RoomRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideRoomDatabase(context: Application): RoomDatabase{
        return RoomDatabase.getInstance(context)
    }

    @Provides
    fun provideDao(roomDatabase: RoomDatabase):RoomDao{
        return roomDatabase.getRoomDao()
    }

    @Singleton
    @Provides
    fun provideRoomRepo(dao: RoomDao) : DatabaseRepository {
        return RoomRepositoryImpl(dao)
    }

}