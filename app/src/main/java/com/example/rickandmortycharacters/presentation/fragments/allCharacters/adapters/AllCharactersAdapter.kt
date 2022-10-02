package com.example.rickandmortycharacters.presentation.fragments.allCharacters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.databinding.RecyclerItemBinding
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import com.example.rickandmortycharacters.utilits.diffUtils.AdapterDiffUtil
import com.example.rickandmortycharacters.utilits.downloadIcon

class AllCharactersAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<AllCharactersAdapter.AllCharactersViewHolder>() {

    private var listAll = mutableListOf<ResultsItem>()

    inner class AllCharactersViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(character: ResultsItem) {
            with(binding) {
                characterName.text = character.name
                characterRace.text = character.species
                characterSex.text = character.gender
                downloadIcon(characterIcon, character.image)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClickAdapter(listAll[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCharactersViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(APP_ACTIVITY), parent, false)
        return AllCharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllCharactersViewHolder, position: Int) {
        holder.bind(listAll[position])
    }

    override fun getItemCount(): Int {
        return listAll.size
    }

    fun setData(newList: List<ResultsItem>) {
        val diffUtil = AdapterDiffUtil(listAll, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        listAll.clear()
        listAll.addAll(newList)
        diffResults.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClickAdapter(model: ResultsItem)
    }
}