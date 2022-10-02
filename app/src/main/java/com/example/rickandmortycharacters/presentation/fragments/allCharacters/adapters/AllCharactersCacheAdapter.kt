package com.example.rickandmortycharacters.presentation.fragments.allCharacters.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.databinding.RecyclerItemBinding
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import com.example.rickandmortycharacters.utilits.APP_ACTIVITY
import com.example.rickandmortycharacters.utilits.diffUtils.AdapterCacheDiffUtil

class AllCharactersCacheAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<AllCharactersCacheAdapter.AllCharactersCacheViewHolder>() {

    private var listCache = mutableListOf<CacheModel>()

    inner class AllCharactersCacheViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener{
        fun bind(character : CacheModel){
            with(binding){
                characterName.text = character.name
                characterRace.text = character.species
                characterSex.text = character.gender
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClickCacheAdapter(listCache[adapterPosition])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllCharactersCacheViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(APP_ACTIVITY), parent, false)
        return AllCharactersCacheViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllCharactersCacheViewHolder, position: Int) {
        holder.bind(listCache[position])
    }

    override fun getItemCount(): Int {
        return listCache.size
    }

    fun setListCache(list: List<CacheModel>) {
        val diffUtil = AdapterCacheDiffUtil(listCache, list)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        listCache.clear()
        listCache.addAll(list)
        diffResults.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClickCacheAdapter(model: CacheModel)
    }
}