package com.example.reposearch.api


import android.util.Log
import com.example.reposearch.data.Repo

class Repository(private val webService: WebService) : IRepository {

    override suspend fun searchOrg(org: String): List<Repo>? {

        if (org.isEmpty()) {
            throw IllegalArgumentException("Please enter an organization")
        }

        var listDefinitions: List<Repo>? = null
        try {
            val response = webService.repos.searchRepo(org, 10)
            when {
                response.isSuccessful -> {
                    listDefinitions = response.body()
                }
                response.errorBody() != null -> {
                    Log.e("RMC", "error ${response.errorBody()}")
                }
                else -> {
                    Log.e("RMC", "something else")
                }
            }
        } catch (e: Exception) {
            Log.i("RMC", "exception")
        } finally {
            return listDefinitions
        }

    }

}