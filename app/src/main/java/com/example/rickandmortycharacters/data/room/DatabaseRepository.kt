package com.example.rickandmortycharacters.data.room

import androidx.lifecycle.LiveData
import com.example.rickandmortycharacters.domain.models.room.CacheModel

interface DatabaseRepository {

    val allCharacters: LiveData<List<CacheModel>>
    suspend fun insert(characters:List<CacheModel>)
    suspend fun delete()
}