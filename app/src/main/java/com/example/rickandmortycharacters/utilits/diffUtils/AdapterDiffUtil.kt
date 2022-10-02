package com.example.rickandmortycharacters.utilits.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmortycharacters.domain.models.retrofit.ResultsItem

class AdapterDiffUtil(private val oldList : List<ResultsItem>,
                      private val newList : List<ResultsItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }
}