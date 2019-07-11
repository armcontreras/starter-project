package com.possible.demo.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.possible.demo.model.GithubRepo
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class GitHubClient private constructor() {
    // regular value
    private val gitHubService: GitHubService

    init {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }

    fun getStarredRepos(userName: String): Observable<List<GithubRepo>> {
        return gitHubService.getStarredRepositories(userName)
    }

    companion object {

        private val GITHUB_BASE_URL = "https://api.github.com/"
        private var instance: GitHubClient? = null

        fun getInstance(): GitHubClient {
            if (instance == null) {
                instance = GitHubClient()
            }

            return instance as GitHubClient
        }
    }
}