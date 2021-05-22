package com.medialink.repoandlivesubmission.data.source.remote.repository

import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.RetrofitClient
import com.medialink.repoandlivesubmission.utils.FakeWeb
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class TvShowRepositoryTest {

    private var mockWebServer = MockWebServer()
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/")) // note the URL is different from production one
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testResponApi() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(FakeWeb.TV_RESPON)
            )

            val response = apiService.getListMovies(ApiConfig.LANGUAGE, 1)

            assertEquals(1, response.page)
            assertEquals(10000, response.totalResults)
            assertEquals(500, response.totalPages)
            assertEquals(60735, response.results?.first()?.id)
        }
    }

    @Mock
    private val myApiService = RetrofitClient.getApiService()

    @Mock
    private val tvRepo = TvShowRepository(myApiService)

    @Test
    fun testGetMovieListFromNetwork() {
        runBlocking {
            val x = tvRepo.getList(1)
            assertNotNull(x)
            assertEquals(20, x.size)
        }
    }

    @Test
    fun testGetMovieDetailFromNetwork() {
        runBlocking {
            val x = tvRepo.getList(1)
            assertNotNull(x)
            val id: Int = x[0].id ?: 0

            val detail = tvRepo.getDetail(id)
            assertNotNull(detail)
            assertEquals(id, detail.id)
        }
    }
}