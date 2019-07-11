package com.possible.demo

import android.arch.lifecycle.ViewModel
import com.possible.demo.model.GithubRepo
import com.possible.demo.network.GitHubClient
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MainViewModel: ViewModel() {

    var subject: PublishSubject<Boolean> = PublishSubject.create()

    fun onCompleteStatus(b: Boolean) {
        subject.onNext(b)
    }

    fun searchGithub(username: String, adapter: GitHubRepoAdapter) {
        if (username.isNotEmpty()) {
           GitHubClient.getInstance()
                   .getStarredRepos(username)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(object: Observer<List<GithubRepo>> {
                       override fun onComplete() {
                           onCompleteStatus(true)
                       }

                       override fun onSubscribe(d: Disposable) {}

                       override fun onNext(t: List<GithubRepo>) {
                           onCompleteStatus(false)
                           adapter.setGitHubRepos(t)
                       }

                       override fun onError(e: Throwable) {
                           onCompleteStatus(true)
                       }
                    })
        }
    }
}