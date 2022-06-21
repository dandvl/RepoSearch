package com.example.reposearch.ui

import com.example.reposearch.api.Repository
import com.example.reposearch.data.Repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepoViewModel(private val repository: Repository) : ViewModel() {

    private var _reposListLD = MutableLiveData<List<Repo>>()
    var reposListLD: LiveData<List<Repo>> = _reposListLD

    private var _loadingLD = MutableLiveData<Boolean>()
    var loadingLD: LiveData<Boolean> = _loadingLD

    private var _error = MutableLiveData<String>()
    var error: LiveData<String> = _error

    private var listNoSorted: List<Repo>? = null

    init {
        _reposListLD.value = mutableListOf()
        _loadingLD.value = false
    }

    fun searchOrg(org: String) = viewModelScope.launch {
        try {
            _loadingLD.postValue(true)
            val repos = repository.searchOrg(org)
            repos?.let {
                _reposListLD.postValue(it)
            } ?: run {
                _reposListLD.postValue(emptyList())
                _error.postValue("No results")
            }
            listNoSorted = _reposListLD.value?.toMutableList()
        } catch (exc: Exception) {
            Log.i("RMC", "Error!!")
        } finally {
            _loadingLD.value = false
        }
    }

    fun sortByDownvotes(){
        _reposListLD.value = _reposListLD.value?.sortedBy { it.stargazers_count }
    }

    fun sortByUpvotes(){
        _reposListLD.value = _reposListLD.value?.sortedByDescending { it.stargazers_count }
    }

    fun originalList(){
        _reposListLD.value = listNoSorted
    }
}