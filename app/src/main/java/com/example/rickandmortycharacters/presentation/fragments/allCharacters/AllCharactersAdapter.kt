package com.example.rickandmortycharacters.presentation.fragments.allCharacters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortycharacters.R
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem
import com.example.rickandmortycharacters.utilits.downloadIcon
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.recycler_item.view.*

class AllCharactersAdapter : RecyclerView.Adapter<AllCharactersAdapter.AllCharactersViewHolder>() {

    private var listAll = mutableListOf<ResultsItem>()

    class AllCharactersViewHolder(view:View):RecyclerView.ViewHolder(view){
        val name: TextView = view.character_name
        val race: TextView = view.character_race
        val sex: TextView = view.character_sex
        val icon: CircleImageView = view.character_icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCharactersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return AllCharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllCharactersViewHolder, position: Int) {
            holder.name.text = listAll[position].name
            holder.race.text = listAll[position].species
            holder.sex.text = listAll[position].gender
            downloadIcon(holder.icon, listAll[position].image)
    }
    override fun onViewAttachedToWindow(holder: AllCharactersViewHolder) {
        holder.itemView.setOnClickListener {
            AllCharactersFragment.clickAdapterElement(listAll[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: AllCharactersViewHolder) {
        holder.itemView.setOnClickListener(null)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return listAll.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list : List<ResultsItem>){
        listAll.clear()
        listAll.addAll(list)
        notifyDataSetChanged()
    }
}