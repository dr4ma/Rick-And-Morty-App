package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.databinding.FragmentAllCharactersBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.domain.usecase.CheckInternetConnection
import com.example.rickandmortycharacters.utilits.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_characters.*

@AndroidEntryPoint
class AllCharactersFragment : Fragment() {
    private var binding: FragmentAllCharactersBinding? = null
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mProgressBarRefresh: ProgressBar
    private lateinit var mRecyclerView: RecyclerView

    private lateinit var checkInternetConnection: CheckInternetConnection
    private lateinit var adapter: AllCharactersAdapter
    private lateinit var adapterCache: AllCharactersCacheAdapter
    private val mViewModel: AllCharactersViewModel by viewModels()
    private lateinit var llm: LinearLayoutManager

    private var isLoading = false
    private lateinit var mObserverList: Observer<List<ResultsItem>>
    private lateinit var mObserverListCache: Observer<List<CacheModel>>
    private lateinit var mCheckInternetConnection: Observer<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bindingRoot = FragmentAllCharactersBinding.inflate(layoutInflater, container, false)
        binding = bindingRoot

        bindingRoot.apply {
            mProgressBar = progressBar
            mProgressBarRefresh = progressBarRefresh
            mRecyclerView = charactersRecyclerView
        }
        return bindingRoot.root
    }

    private fun initFields() {
        llm = LinearLayoutManager(context)
        checkInternetConnection = CheckInternetConnection(context)
        adapter = AllCharactersAdapter()
        adapterCache = AllCharactersCacheAdapter()
        mRecyclerView.layoutManager = llm
    }

    private fun initObservers() {
        // observer for Retrofit result Livedata
        mObserverList = Observer { list ->
            if (mRecyclerView.adapter == null) {
                mRecyclerView.adapter = adapter
            }
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
                    mViewModel.resultCacheList.removeObserver(mObserverListCache)
                    mViewModel.resultCharactersList.observe(APP_ACTIVITY, mObserverList)
                }

            } else {
                mViewModel.resultCharactersList.removeObserver(mObserverList)
                mViewModel.resultCacheList.observe(APP_ACTIVITY, mObserverListCache)
                showToast(getString(R.string.no_connection))
            }
        }
    }

    private fun initListener(){
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = llm.childCount
                    val itemPosition = llm.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount
                    if (!isLoading) {
                        if ((visibleItemCount + itemPosition) >= total) {
                            mProgressBarRefresh.visibility = View.VISIBLE
                            isLoading = true
                            mViewModel.getAllCharacters {
                                mProgressBarRefresh.visibility = View.INVISIBLE
                                isLoading = false
                            }
                        }
                    }
                }
            }
        })
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
        initListener()
        checkInternetConnection.observe(APP_ACTIVITY, mCheckInternetConnection)
        switchEnableRecyclerView(false)
        mProgressBarRefresh.visibility = View.INVISIBLE
    }


    override fun onStop() {
        super.onStop()
        mViewModel.resultCacheList.removeObserver(mObserverListCache)
        mViewModel.resultCharactersList.removeObserver(mObserverList)
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