package com.example.rickandmortycharacters.presentation.fragments.singleCharacter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmortycharacters.databinding.FragmentSingleCharacterBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.utilits.*

class SingleCharacterFragment : Fragment() {

    private var binding: FragmentSingleCharacterBinding? = null
    private val mBinding get() = binding!!
    private lateinit var mCurrentCharacter: ResultsItem
    private lateinit var mCurrentCharacterCache: CacheModel
    private var mCharacterType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleCharacterBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        APP_ACTIVITY.supportActionBar?.setDisplayShowHomeEnabled(true)
        mCharacterType = arguments?.getString(KEY_CACHE)!!

        initData()
    }

    private fun initData() {
        mBinding.apply {
            when (mCharacterType) {
                TYPE_RETROFIT -> {
                    mCurrentCharacter = arguments?.getSerializable(KEY_CHARACTER) as ResultsItem
                    downloadIcon(currentCharacterIcon, mCurrentCharacter.image)
                    currentCharacterName.text = mCurrentCharacter.name
                    gender.text = mCurrentCharacter.gender
                    species.text = mCurrentCharacter.species
                    status.text = mCurrentCharacter.status
                    location.text = mCurrentCharacter.location.name
                    episodes.text = mCurrentCharacter.episode?.size.toString()
                }
                TYPE_CACHE -> {
                    mCurrentCharacterCache = arguments?.getSerializable(KEY_CHARACTER) as CacheModel
                    currentCharacterName.text = mCurrentCharacterCache.name
                    gender.text = mCurrentCharacterCache.gender
                    species.text = mCurrentCharacterCache.species
                    status.text = mCurrentCharacterCache.status
                    location.text = mCurrentCharacterCache.locationName
                    episodes.text = mCurrentCharacterCache.episodes.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}