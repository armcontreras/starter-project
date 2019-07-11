package com.possible.demo.network


import com.possible.demo.model.GithubRepo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {
    @GET("users/{user}/followers")
    fun getStarredRepositories(@Path("user") userName: String): Observable<List<GithubRepo>>
}