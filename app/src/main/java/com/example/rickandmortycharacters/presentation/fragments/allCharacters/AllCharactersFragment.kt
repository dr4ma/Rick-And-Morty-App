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
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import com.example.rickandmortycharacters.utilits.KEY_CACHE
import com.example.rickandmortycharacters.utilits.KEY_CHARACTER
import com.example.rickandmortycharacters.utilits.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCharactersFragment : Fragment() {

    private var binding : FragmentAllCharactersBinding? = null
    private val mBinding get() = binding!!
    private val mViewModel : AllCharactersViewModel by viewModels()
    private lateinit var recyclerView : RecyclerView
    private lateinit var checkInternetConnection : CheckInternetConnection
    private lateinit var adapter: AllCharactersAdapter
    private lateinit var adapterCache: AllCharactersCacheAdapter
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mObserverList: Observer<List<ResultsItem>>
    private lateinit var mObserverListCache: Observer<List<CacheModel>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCharactersBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        APP_ACTIVITY.supportActionBar?.setDisplayShowHomeEnabled(false)
        mProgressBar = mBinding.progressBar
        recyclerView = mBinding.charactersRecyclerView
        checkInternetConnection = CheckInternetConnection()
        switchEnableRecyclerView(false)
        initRecycleView()
    }

    private fun initRecycleView(){
        adapter = AllCharactersAdapter()
        adapterCache = AllCharactersCacheAdapter()
        checkInternetConnection.observe(APP_ACTIVITY, Observer { isConnected ->
            if(isConnected){
                mViewModel.getAllCharacters{
                    mObserverList = Observer {
                        recyclerView.adapter = adapter
                        adapter.setList(it)
                        switchEnableRecyclerView(true)
                        mViewModel.insertCharacters(it)
                    }
                    mViewModel.allCharactersList.observe(APP_ACTIVITY, mObserverList)
                }
            }
            else{
                mObserverListCache = Observer {
                    recyclerView.adapter = adapterCache
                    adapterCache.setListCache(it)
                    switchEnableRecyclerView(true)
                    showToast(getString(R.string.no_connection))
                }
                mViewModel.getCacheList.observe(APP_ACTIVITY, mObserverListCache)
            }
        })
    }

    private fun switchEnableRecyclerView(load : Boolean){
        when(load){
            true -> {
                mProgressBar.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
            }
            false -> {
                mProgressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun clickAdapterElement(resultsItem: ResultsItem) {
            val bundle = Bundle()
            bundle.putSerializable(KEY_CHARACTER, resultsItem)
            bundle.putBoolean(KEY_CACHE, true)
            APP_ACTIVITY.mNavController.navigate(R.id.action_allCharactersFragment_to_singleCharacterFragment, bundle)
        }

        fun clickAdapterCache(cacheModel: CacheModel) {
            val bundle = Bundle()
            bundle.putSerializable(KEY_CHARACTER, cacheModel)
            bundle.putBoolean(KEY_CACHE, false)
            APP_ACTIVITY.mNavController.navigate(R.id.action_allCharactersFragment_to_singleCharacterFragment, bundle)
        }
    }

}