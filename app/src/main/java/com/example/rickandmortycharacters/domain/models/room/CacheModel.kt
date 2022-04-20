package com.example.rickandmortycharacters.domain.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "characters_table")
class CacheModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var name: String = "",
    @ColumnInfo var species: String = "",
    @ColumnInfo var gender: String = "",
    @ColumnInfo var locationName: String = "",
    @ColumnInfo var status: String = "",
    @ColumnInfo var episodes: Int = 0
) : Serializable