package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.databinding.FragmentAllCharactersBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.presentation.fragments.allCharacters.adapters.AllCharactersAdapter
import com.example.rickandmortycharacters.presentation.fragments.allCharacters.adapters.AllCharactersCacheAdapter
import com.example.rickandmortycharacters.presentation.fragments.allCharacters.viewModel.AllCharactersViewModel
import com.example.rickandmortycharacters.presentation.fragments.allCharacters.viewModel.AllCharactersViewModelFactory
import com.example.rickandmortycharacters.utilits.*

class AllCharactersFragment : Fragment(), AllCharactersAdapter.OnItemClickListener,
    AllCharactersCacheAdapter.OnItemClickListener {
    private var binding: FragmentAllCharactersBinding? = null
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mProgressBarRefresh: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mToolbar: Toolbar

    private lateinit var mViewModel: AllCharactersViewModel
    private lateinit var mAdapter: AllCharactersAdapter
    private lateinit var mAdapterCache: AllCharactersCacheAdapter
    private lateinit var llm: LinearLayoutManager

    private var isLoading = false
    private var connectionChanged = false
    private var isSearching = false
    private var isConnected = true
    private lateinit var mObserverList: Observer<List<ResultsItem>>
    private lateinit var mObserverListCache: Observer<List<CacheModel>>

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
            mToolbar = toolbar
        }

        return bindingRoot.root
    }

    override fun onStart() {
        super.onStart()

        initFields()
        initObservers()
        initScrollListener()
        checkConnection()
        observNetworkConnection()
    }

    private fun initFields() {
        initToolbar()
        llm = LinearLayoutManager(context)
        mAdapter = AllCharactersAdapter(this)
        mAdapterCache = AllCharactersCacheAdapter(this)
        mRecyclerView.layoutManager = llm
        mRecyclerView.isNestedScrollingEnabled = false
        mProgressBarRefresh.visibility = View.INVISIBLE
        mViewModel = ViewModelProvider(this, AllCharactersViewModelFactory())[AllCharactersViewModel::class.java]
        mAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun initToolbar() {
        mToolbar.title = getString(R.string.app_name)
        mToolbar.menu
                .findItem(R.id.action_search)
                .actionView
                .let { it as SearchView }
                .setOnQueryTextListener(SearchViewListener {
                    initSearch(it)
                })
    }

    private fun initSearch(text : String){
        isSearching = text.isNotEmpty()
        if(isConnected){
            mViewModel.initSearchCharacter(text) { searchList ->
                mAdapter.setData(searchList)
            }
        }
        else{
            mViewModel.initSearchCacheCharacter(text) { searchList ->
                mAdapterCache.setListCache(searchList)
            }
        }
    }

    private fun initObservers() {
        // observer for Retrofit result Livedata
        mObserverList = Observer { list ->
            if (mRecyclerView.adapter == null) {
                mRecyclerView.adapter = mAdapter
            }
            mAdapter.setData(list)
            mViewModel.insertCharacters(list)
            mProgressBar.visibility = View.INVISIBLE
        }
        // observer for Room result Livedata
        mObserverListCache = Observer { list ->
            mRecyclerView.adapter = mAdapterCache
            mAdapterCache.setListCache(list)
            mProgressBar.visibility = View.INVISIBLE
        }
    }

    private fun observNetworkConnection() {
        mViewModel.observConnection { status ->
            if (status == StatusConnection.UNAVAILABLE || status == StatusConnection.LOST) {
                connectionChanged = true
                isConnected = false
                mViewModel.resultCharactersList.removeObserver(mObserverList)
                mViewModel.resultCacheList.observe(APP_ACTIVITY, mObserverListCache)
                showSnackBar(mRecyclerView, getString(R.string.no_connection), R.color.colorRed)
            } else if (status == StatusConnection.AVAILABLE) {
                if (connectionChanged) {
                    connectionChanged = false
                    showSnackBar(mRecyclerView, getString(R.string.connected), R.color.colorGreen)
                }
                isConnected = true
                mViewModel.getAllCharacters {
                    mRecyclerView.adapter = mAdapter
                    mViewModel.resultCacheList.removeObserver(mObserverListCache)
                    mViewModel.resultCharactersList.observe(APP_ACTIVITY, mObserverList)
                }
            }
        }
    }

    private fun checkConnection() {
        // Единоразовая проверка на интернет, так как в связи с багом блок override fun onUnavailable() не срабатывает
        // при первом запуске приложения
        mViewModel.checkConnection {
            isConnected = false
            mViewModel.resultCharactersList.removeObserver(mObserverList)
            mViewModel.resultCacheList.observe(APP_ACTIVITY, mObserverListCache)
            mProgressBar.visibility = View.INVISIBLE
            if (!connectionChanged) {
                showSnackBar(mRecyclerView, getString(R.string.no_connection), R.color.colorRed)
                connectionChanged = true
            }
        }
    }

    private fun initScrollListener() {
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = llm.childCount
                    val itemPosition = llm.findFirstCompletelyVisibleItemPosition()
                    val total = mAdapter.itemCount
                    if(isConnected){
                        if (!isSearching) {
                            if ((visibleItemCount + itemPosition) > total) {
                                if (!isLoading) {
                                    isLoading = true
                                    mProgressBarRefresh.visibility = View.VISIBLE
                                    mViewModel.getAllCharacters {
                                        mProgressBarRefresh.visibility = View.INVISIBLE
                                    }
                                }
                            } else {
                                isLoading = false
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onItemClickAdapter(model: ResultsItem) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_CHARACTER, model)
        bundle.putString(KEY_CACHE, TYPE_RETROFIT)
        APP_ACTIVITY.mNavController.navigate(
            R.id.action_allCharactersFragment_to_singleCharacterFragment,
            bundle
        )
    }

    override fun onItemClickCacheAdapter(model: CacheModel) {
        val bundle = Bundle()
        bundle.putSerializable(KEY_CHARACTER, model)
        bundle.putString(KEY_CACHE, TYPE_CACHE)
        APP_ACTIVITY.mNavController.navigate(
            R.id.action_allCharactersFragment_to_singleCharacterFragment,
            bundle
        )
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
}