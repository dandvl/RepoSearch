package com.example.reposearch

import com.example.reposearch.api.RepoService
import com.example.reposearch.api.Repository
import com.example.reposearch.api.WebService
import com.example.reposearch.data.Repo
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.io.BufferedReader
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var webService : WebService

    @Mock
    private lateinit var repos : RepoService

    @Test(expected = IllegalArgumentException::class)
    fun `empty String should throw exception`(): Unit = runBlocking {
        val fakeWebService = Mockito.mock(WebService::class.java)
        Repository(fakeWebService).searchOrg("")
    }

    @Test
    fun `term String should retrieve results`() = runBlocking {
        val org = "google"

        val jsonPath = javaClass.classLoader?.getResource("fakeResponse.json")?.path
        val jsonBuffered: BufferedReader = File(jsonPath!!).bufferedReader()
        val jsonStr = jsonBuffered.use { it.readText() }

        val collectionType = object : TypeToken<List<Repo>>() {}.type
        val fakeResponse: List<Repo> = GsonBuilder().create().fromJson(jsonStr, collectionType)

        val retrofitResponse = Response.success(fakeResponse)

        `when`(webService.repos).thenReturn(repos)
        `when`(repos.searchRepo(org,10)).thenReturn(retrofitResponse)
        val res = Repository(webService).searchOrg(org)

        assertEquals(2, res?.size)
    }

}