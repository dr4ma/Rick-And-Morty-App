package com.example.rickandmortycharacters.presentation.fragments.singleCharacter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmortycharacters.databinding.FragmentSingleCharacterBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.utilits.*
import kotlinx.android.synthetic.main.fragment_all_characters.*

class SingleCharacterFragment : Fragment() {

    private var binding: FragmentSingleCharacterBinding? = null
    private lateinit var mCurrentCharacter: ResultsItem
    private lateinit var mCurrentCharacterCache: CacheModel
    private var mCharacterType: String = ""

    private lateinit var mToolbar : Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bindingRoot = FragmentSingleCharacterBinding.inflate(layoutInflater, container, false)
        binding = bindingRoot

        bindingRoot.apply {
            mToolbar = toolbar
        }

        return bindingRoot.root
    }

    override fun onStart() {
        super.onStart()
        mCharacterType = arguments?.getString(KEY_CACHE)!!
        initData()
    }

    private fun initData() {
        binding?.apply {
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
                    initToolbar(mCurrentCharacter.name)
                }
                TYPE_CACHE -> {
                    mCurrentCharacterCache = arguments?.getSerializable(KEY_CHARACTER) as CacheModel
                    currentCharacterName.text = mCurrentCharacterCache.name
                    gender.text = mCurrentCharacterCache.gender
                    species.text = mCurrentCharacterCache.species
                    status.text = mCurrentCharacterCache.status
                    location.text = mCurrentCharacterCache.locationName
                    episodes.text = mCurrentCharacterCache.episodes.toString()
                    initToolbar( mCurrentCharacterCache.name)
                }
            }
        }
    }

    private fun initToolbar(name : String){
        with(mToolbar){
            setupWithNavController(findNavController())
            title = name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}