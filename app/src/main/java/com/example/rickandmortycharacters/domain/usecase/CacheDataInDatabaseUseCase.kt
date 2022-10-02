package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.usecase.interfaces.CacheDataInDatabaseInterface
import com.example.rickandmortycharacters.utilits.ServiceLocator

class CacheDataInDatabaseUseCase : CacheDataInDatabaseInterface {

    private val databaseRepository = ServiceLocator.get(DatabaseRepository::class) as DatabaseRepository
    override val getCacheList = databaseRepository.allCharacters

    override suspend fun insertData(characters: List<CacheModel>, onSuccess: () -> Unit) {
        databaseRepository.insert(characters){
            onSuccess()
        }
    }

    override suspend fun deleteData(onSuccess: () -> Unit) {
        databaseRepository.delete(){
            onSuccess()
        }
    }
}