package com.example.reposearch.api

import com.example.reposearch.data.Repo

interface IRepository {
    suspend fun searchOrg(org : String) : List<Repo>?
}