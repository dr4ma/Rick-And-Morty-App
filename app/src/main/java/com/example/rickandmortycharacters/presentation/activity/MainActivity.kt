package com.example.rickandmortycharacters.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.databinding.ActivityMainBinding
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    lateinit var mNavController: NavController

    private var binding : ActivityMainBinding? = null
    val mBinding get() = binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        APP_ACTIVITY = this
        mNavController = Navigation.findNavController(APP_ACTIVITY, R.id.nav_host_fragment_container)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}