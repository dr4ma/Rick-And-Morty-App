package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.domain.models.ResultsItem
import com.example.rickandmortycharacters.databinding.FragmentAllCharactersBinding
import com.example.rickandmortycharacters.domain.usecase.CheckInternetConnection
import com.example.rickandmortycharacters.presentation.activity.MainActivity
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
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
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mObserverList: Observer<List<ResultsItem>>

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
        recyclerView.adapter = adapter
        checkInternetConnection.observe(APP_ACTIVITY, Observer { isConnected ->
            if(isConnected){
                mViewModel.getAllCharacters{
                    mObserverList = Observer {
                        adapter.setList(it)
                        switchEnableRecyclerView(true)
                    }
                    mViewModel.allCharactersList.observe(APP_ACTIVITY, mObserverList)
                }
            }
            else{
                showToast(getString(R.string.no_connection))
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
            APP_ACTIVITY.mNavController.navigate(R.id.action_allCharactersFragment_to_singleCharacterFragment, bundle)
        }
    }

}