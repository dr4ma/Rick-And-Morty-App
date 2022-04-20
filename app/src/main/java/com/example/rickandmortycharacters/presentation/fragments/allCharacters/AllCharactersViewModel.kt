package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.usecase.CacheDataInDatabaseUseCase
import com.example.rickandmortycharacters.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCharactersViewModel @Inject constructor(
    private val repo: GetAllCharactersUseCase,
    private val cacheDataInDatabaseUseCase: CacheDataInDatabaseUseCase
) : ViewModel() {

    val allCharactersList: MutableLiveData<MutableList<ResultsItem>> = MutableLiveData()
    private val cacheList = mutableListOf<CacheModel>()
    val getCacheList = cacheDataInDatabaseUseCase.getCacheList

    fun getAllCharacters(onSuccess: () -> Unit) {
        viewModelScope.launch {
            allCharactersList.value = repo.getAllCharacters()
        }
        onSuccess()
    }

    fun insertCharacters(characters: List<ResultsItem>) {
        deleteCharacters()
        viewModelScope.launch {
            characters.forEach {
                val cacheModel = CacheModel(
                    name = it.name,
                    species = it.species,
                    gender = it.gender,
                    locationName = it.location.name,
                    status = it.status,
                    episodes = it.episode?.size!!
                )
                cacheList.add(cacheModel)
            }
            cacheDataInDatabaseUseCase.insertData(cacheList)
            cacheList.clear()
        }
    }

    private fun deleteCharacters(){
        viewModelScope.launch {
            cacheDataInDatabaseUseCase.deleteData()

        }
    }
}