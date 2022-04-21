package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import javax.inject.Inject

class CacheDataInDatabaseUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {

    val getCacheList = databaseRepository.allCharacters

    suspend fun insertData(characters: List<CacheModel>, onSuccess: () -> Unit) {
        databaseRepository.insert(characters){
            onSuccess()
        }
    }

    suspend fun deleteData(onSuccess: () -> Unit) {
        databaseRepository.delete(){
            onSuccess()
        }
    }
}