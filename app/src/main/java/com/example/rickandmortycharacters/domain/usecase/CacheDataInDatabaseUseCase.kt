package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.data.room.DatabaseRepository
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import javax.inject.Inject

class CacheDataInDatabaseUseCase @Inject constructor(private val databaseRepository: DatabaseRepository) {

    val getCacheList = databaseRepository.allCharacters

    suspend fun insertData(characters: List<CacheModel>) {
        databaseRepository.insert(characters)
    }

    suspend fun deleteData() {
        databaseRepository.delete()
    }
}