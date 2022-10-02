package com.example.rickandmortycharacters.presentation.fragments.allCharacters.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.usecase.interfaces.CacheDataInDatabaseInterface
import com.example.rickandmortycharacters.domain.usecase.interfaces.GetAllCharactersInterface
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import com.example.rickandmortycharacters.utilits.StatusConnection
import com.example.rickandmortycharacters.utilits.TAG
import com.example.rickandmortycharacters.utilits.networkConnection.CheckInternetConnectionInMoment
import com.example.rickandmortycharacters.utilits.networkConnection.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllCharactersViewModel(
    private val getAllCharactersUseCase : GetAllCharactersInterface,
    private val cacheDataInDatabaseUseCase: CacheDataInDatabaseInterface
) : ViewModel() {

    private var checkConnectionInMoment = CheckInternetConnectionInMoment()
    private val sumCharacters = mutableListOf<ResultsItem>()
    private val allCharactersList: MutableLiveData<MutableList<ResultsItem>> = MutableLiveData()
    val resultCharactersList: LiveData<MutableList<ResultsItem>> = allCharactersList
    private var page = 0
    private val cacheList = arrayListOf<CacheModel>()

    private val getCacheList = cacheDataInDatabaseUseCase.getCacheList
    val resultCacheList = getCacheList

    private val connectivityObserver = NetworkConnectivityObserver(APP_ACTIVITY)

    fun getAllCharacters(onSuccess: () -> Unit) {
        page++
        viewModelScope.launch {
            try {
                getAllCharactersUseCase.getAllCharacters(page = page)
                    .collect {
                        sumCharacters.addAll(it)
                    }
                allCharactersList.value = sumCharacters
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
            onSuccess()
        }
    }

    fun initSearchCharacter(name : String, function: (MutableList<ResultsItem>) -> Unit){
        val list = resultCharactersList.value?.filter { it.name.lowercase().contains(name.lowercase()) } as MutableList
        function(list)
    }

    fun initSearchCacheCharacter(name : String, function: (MutableList<CacheModel>) -> Unit){
        val list = resultCacheList.value?.filter { it.name.lowercase().contains(name.lowercase()) } as MutableList
        function(list)
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
            val newList: List<CacheModel> = ArrayList<CacheModel>(cacheList)

            cacheDataInDatabaseUseCase.insertData(newList) {
                Log.i(TAG, "insert was successful")
            }
            cacheList.clear()
        }
    }

    private fun deleteCharacters() {
        viewModelScope.launch {
            cacheDataInDatabaseUseCase.deleteData() {
                Log.i(TAG, "delete was successful")
            }
        }
    }

    fun checkConnection(function:() -> Unit) {
        if (!checkConnectionInMoment.check()) {
            function()
        }
    }

    fun observConnection(function: (status: StatusConnection) -> Unit) {
        viewModelScope.launch {
            connectivityObserver.observe()
            .collect {
                function(it)
            }
        }
    }
}