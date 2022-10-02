package com.example.rickandmortycharacters.utilits.networkConnection

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import javax.inject.Inject

class CheckInternetConnectionInMoment {
    private var connectivity : ConnectivityManager? = null
    private var info : NetworkInfo? = null

    fun check() : Boolean{
        var checkResult = false
        connectivity = APP_ACTIVITY.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(connectivity != null){
            info = connectivity!!.activeNetworkInfo
            if(info != null){
                checkResult = info!!.state == NetworkInfo.State.CONNECTED
            }
        }
        return checkResult
    }
}