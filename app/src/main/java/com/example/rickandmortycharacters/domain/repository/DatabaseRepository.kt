package com.example.rickandmortycharacters.domain.repository

import androidx.lifecycle.LiveData
import com.example.rickandmortycharacters.domain.models.room.CacheModel

interface DatabaseRepository {

    val allCharacters: LiveData<List<CacheModel>>

    suspend fun insert(characters: List<CacheModel>, onSuccess: () -> Unit)

    suspend fun delete(onSuccess: () -> Unit)
}