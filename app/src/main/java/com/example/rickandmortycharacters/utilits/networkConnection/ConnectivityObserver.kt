package com.example.rickandmortycharacters.utilits.networkConnection

import com.example.rickandmortycharacters.utilits.StatusConnection
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe() : Flow<StatusConnection>
}