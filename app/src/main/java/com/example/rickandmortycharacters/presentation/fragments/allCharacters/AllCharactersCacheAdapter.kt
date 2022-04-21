package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.domain.models.room.CacheModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.recycler_item.view.*

class AllCharactersCacheAdapter : RecyclerView.Adapter<AllCharactersCacheAdapter.AllCharactersCacheViewHolder>() {

    private var listCache = mutableListOf<CacheModel>()

    class AllCharactersCacheViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.character_name
        val race: TextView = view.character_race
        val sex: TextView = view.character_sex
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllCharactersCacheAdapter.AllCharactersCacheViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return AllCharactersCacheAdapter.AllCharactersCacheViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AllCharactersCacheAdapter.AllCharactersCacheViewHolder,
        position: Int
    ) {
        holder.name.text = listCache[position].name
        holder.race.text = listCache[position].species
        holder.sex.text = listCache[position].gender
    }

    override fun onViewAttachedToWindow(holder: AllCharactersCacheAdapter.AllCharactersCacheViewHolder) {
        holder.itemView.setOnClickListener {
            AllCharactersFragment.clickAdapterCache(listCache[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: AllCharactersCacheAdapter.AllCharactersCacheViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return listCache.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListCache(list: List<CacheModel>) {
        listCache.clear()
        listCache.addAll(list)
        notifyDataSetChanged()
    }
}