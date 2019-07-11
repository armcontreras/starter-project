package com.possible.demo

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var adapter = GitHubRepoAdapter()
    private var disposable: Disposable?= null

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var editTextUsername: EditText
    private lateinit var buttonSearch: Button
    private lateinit var inProgress: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)

        val model = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initViews()
        searchButton(model)
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        editTextUsername = findViewById(R.id.edit_text_username)
        buttonSearch = findViewById(R.id.button_search)
        inProgress = findViewById(R.id.progressBar)
    }

    private fun searchButton(model: MainViewModel) {
        buttonSearch.setOnClickListener {
            val username = editTextUsername.text.toString()
            inProgress.visibility = View.VISIBLE
            updateProgressLoader(model, inProgress)
            model.searchGithub(username, adapter)
        }
    }

    private fun updateProgressLoader(model: MainViewModel, inProgress: ProgressBar) {
        disposable = model.subject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it) {
                        inProgress.visibility = View.INVISIBLE
                    }
                }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}
