package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.databinding.FragmentAllCharactersBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.usecase.CheckInternetConnection
import com.example.rickandmortycharacters.utilits.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCharactersFragment : Fragment() {
    private var binding: FragmentAllCharactersBinding? = null
    private val mBinding get() = binding!!
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var checkInternetConnection: CheckInternetConnection
    private lateinit var adapter: AllCharactersAdapter
    private lateinit var adapterCache: AllCharactersCacheAdapter
    private val mViewModel: AllCharactersViewModel by viewModels()

    private lateinit var mObserverList: Observer<List<ResultsItem>>
    private lateinit var mObserverListCache: Observer<List<CacheModel>>
    private lateinit var mCheckInternetConnection: Observer<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    private fun initFields() {
        mProgressBar = mBinding.progressBar
        mRecyclerView = mBinding.charactersRecyclerView
        checkInternetConnection = CheckInternetConnection()
        adapter = AllCharactersAdapter()
        adapterCache = AllCharactersCacheAdapter()
    }

    private fun initObservers() {
        // observer for Retrofit result Livedata
        mObserverList = Observer { list ->
            mRecyclerView.adapter = adapter
            adapter.setList(list)
            switchEnableRecyclerView(true)
            mViewModel.insertCharacters(list)
        }
        // observer for Room result Livedata
        mObserverListCache = Observer { list ->
            mRecyclerView.adapter = adapterCache
            adapterCache.setListCache(list)
            switchEnableRecyclerView(true)
        }
        // observer for connection Livedata
        mCheckInternetConnection = Observer { isConnected ->
            if (isConnected) {
                mViewModel.getAllCharacters {
                    mViewModel.getCacheList.removeObserver(mObserverListCache)
                    mViewModel.allCharactersList.observe(APP_ACTIVITY, mObserverList)
                }
            } else {
                mViewModel.allCharactersList.removeObserver(mObserverList)
                mViewModel.getCacheList.observe(APP_ACTIVITY, mObserverListCache)
                showToast(getString(R.string.no_connection))
            }
        }
    }

    private fun switchEnableRecyclerView(load: Boolean) {
        when (load) {
            true -> {
                mProgressBar.visibility = View.INVISIBLE
                mRecyclerView.visibility = View.VISIBLE
            }
            false -> {
                mProgressBar.visibility = View.VISIBLE
                mRecyclerView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        APP_ACTIVITY.supportActionBar?.setDisplayShowHomeEnabled(false)

        initFields()
        initObservers()
        checkInternetConnection.observe(APP_ACTIVITY, mCheckInternetConnection)
        switchEnableRecyclerView(false)
    }

    override fun onStop() {
        super.onStop()
        mViewModel.getCacheList.removeObserver(mObserverListCache)
        mViewModel.allCharactersList.removeObserver(mObserverList)
    }

    override fun onDestroy() {
        super.onDestroy()
        checkInternetConnection.removeObserver(mCheckInternetConnection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun clickAdapterElement(resultsItem: ResultsItem) {
            val bundle = Bundle()
            bundle.putSerializable(KEY_CHARACTER, resultsItem)
            bundle.putString(KEY_CACHE, TYPE_RETROFIT)
            APP_ACTIVITY.mNavController.navigate(
                R.id.action_allCharactersFragment_to_singleCharacterFragment,
                bundle
            )
        }

        fun clickAdapterCache(cacheModel: CacheModel) {
            val bundle = Bundle()
            bundle.putSerializable(KEY_CHARACTER, cacheModel)
            bundle.putString(KEY_CACHE, TYPE_CACHE)
            APP_ACTIVITY.mNavController.navigate(
                R.id.action_allCharactersFragment_to_singleCharacterFragment,
                bundle
            )
        }
    }

}