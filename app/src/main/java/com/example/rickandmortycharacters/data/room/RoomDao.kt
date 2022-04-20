package com.example.rickandmortycharacters.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.rickandmortycharacters.domain.models.room.CacheModel

@Dao
interface RoomDao {

    @Query("SELECT * FROM characters_table")
    fun getAllCharacters(): LiveData<List<CacheModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characters: List<CacheModel>)

    @Query("DELETE FROM characters_table")
    suspend fun delete()
}