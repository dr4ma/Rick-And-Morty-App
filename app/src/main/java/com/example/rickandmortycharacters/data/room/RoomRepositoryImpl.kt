package com.example.rickandmortycharacters.data.room

import androidx.lifecycle.LiveData
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.repository.DatabaseRepository
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import javax.inject.Inject

class RoomRepositoryImpl : DatabaseRepository {

    private val dao = RoomDatabase.getInstance(APP_ACTIVITY).getRoomDao()

    override val allCharacters: LiveData<List<CacheModel>>
        get() = dao.getAllCharacters()

    override suspend fun insert(characters: List<CacheModel>, onSuccess: () -> Unit) {
        dao.insert(characters = characters)
        onSuccess()
    }

    override suspend fun delete(onSuccess: () -> Unit) {
        dao.delete()
        onSuccess()
    }
}