package com.example.rickandmortycharacters.presentation.fragments.singleCharacter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmortycharacters.databinding.FragmentSingleCharacterBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import com.example.rickandmortycharacters.utilits.KEY_CACHE
import com.example.rickandmortycharacters.utilits.KEY_CHARACTER
import com.example.rickandmortycharacters.utilits.downloadIcon

class SingleCharacterFragment : Fragment() {

    private var binding: FragmentSingleCharacterBinding? = null
    private val mBinding get() = binding!!
    private lateinit var mCurrentCharacter: ResultsItem
    private lateinit var mCurrentCharacterCache: CacheModel
    private var mCharacterType: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleCharacterBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        APP_ACTIVITY.supportActionBar?.setDisplayShowHomeEnabled(true)
        mCharacterType = arguments?.getBoolean(KEY_CACHE)!!
        if(mCharacterType){
            mCurrentCharacter = arguments?.getSerializable(KEY_CHARACTER) as ResultsItem
        }
        else{
            mCurrentCharacterCache = arguments?.getSerializable(KEY_CHARACTER) as CacheModel
        }
        initData()
    }

    private fun initData() {
        mBinding.apply {
            if (mCharacterType) {
                downloadIcon(currentCharacterIcon, mCurrentCharacter.image)
                currentCharacterName.text = mCurrentCharacter.name
                gender.text = mCurrentCharacter.gender
                species.text = mCurrentCharacter.species
                status.text = mCurrentCharacter.status
                location.text = mCurrentCharacter.location.name
                episodes.text = mCurrentCharacter.episode?.size.toString()
            }
            else{
                currentCharacterName.text = mCurrentCharacterCache.name
                gender.text = mCurrentCharacterCache.gender
                species.text = mCurrentCharacterCache.species
                status.text = mCurrentCharacterCache.status
                location.text = mCurrentCharacterCache.locationName
                episodes.text = mCurrentCharacterCache.episodes.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}