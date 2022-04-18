package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycharacters.domain.models.ResultsItem
import com.example.rickandmortycharacters.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCharactersViewModel @Inject constructor(private val repo: GetAllCharactersUseCase) : ViewModel() {

    val allCharactersList: MutableLiveData<MutableList<ResultsItem>> = MutableLiveData()

    fun getAllCharacters(onSuccess:() -> Unit) {
        viewModelScope.launch {
            allCharactersList.value = repo.getAllCharacters()
        }
        onSuccess()
    }
}