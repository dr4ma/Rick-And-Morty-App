package com.example.rickandmortycharacters.data.room

import androidx.lifecycle.LiveData
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import javax.inject.Inject

class RoomRepository @Inject constructor(private val roomDao: RoomDao) : DatabaseRepository {


    override val allCharacters: LiveData<List<CacheModel>>
        get() = roomDao.getAllCharacters()

    override suspend fun insert(characters: List<CacheModel>) {
        roomDao.insert(characters = characters)
    }

    override suspend fun delete() {
        roomDao.delete()
    }
}