package com.example.rickandmortycharacters.domain.usecase.interfaces

import androidx.lifecycle.LiveData
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.utilits.ServiceLocator

interface CacheDataInDatabaseInterface : ServiceLocator.Service {

    val getCacheList: LiveData<List<CacheModel>>
    suspend fun insertData(characters: List<CacheModel>, onSuccess: () -> Unit)
    suspend fun deleteData(onSuccess: () -> Unit)
}