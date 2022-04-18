package com.example.rickandmortycharacters.presentation.fragments.singleCharacter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmortycharacters.databinding.FragmentSingleCharacterBinding
import com.example.rickandmortycharacters.domain.models.ResultsItem
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import com.example.rickandmortycharacters.utilits.KEY_CHARACTER
import com.example.rickandmortycharacters.utilits.downloadIcon

class SingleCharacterFragment : Fragment() {

    private var binding: FragmentSingleCharacterBinding? = null
    private val mBinding get() = binding!!
    private lateinit var mCurrentCharacter: ResultsItem

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
        mCurrentCharacter = arguments?.getSerializable(KEY_CHARACTER) as ResultsItem

        initData()
    }

    private fun initData() {
        mBinding.apply {
            downloadIcon(currentCharacterIcon, mCurrentCharacter.image)
            currentCharacterName.text = mCurrentCharacter.name
            gender.text = mCurrentCharacter.gender
            species.text = mCurrentCharacter.species
            status.text = mCurrentCharacter.status
            location.text = mCurrentCharacter.location.name
            episodes.text = mCurrentCharacter.episode?.size.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}