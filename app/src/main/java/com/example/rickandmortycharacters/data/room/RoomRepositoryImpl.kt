package com.example.rickandmortycharacters.data.room

import androidx.lifecycle.LiveData
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val roomDao: RoomDao) : DatabaseRepository {


    override val allCharacters: LiveData<List<CacheModel>>
        get() = roomDao.getAllCharacters()

    override suspend fun insert(characters: List<CacheModel>, onSuccess: () -> Unit) {
        roomDao.insert(characters = characters)
        onSuccess()
    }

    override suspend fun delete(onSuccess: () -> Unit) {
        roomDao.delete()
        onSuccess()
    }
}