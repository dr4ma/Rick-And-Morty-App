package com.example.rickandmortycharacters.utilits

import androidx.appcompat.widget.SearchView

class SearchViewListener (private val function:(text : String) -> Unit) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        function(newText ?: "")
        return true
    }
}