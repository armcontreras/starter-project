package com.possible.demo

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {


    private var adapter = GitHubRepoAdapter()
    private var disposable: Disposable?= null

//    @Inject
//    lateinit var model: MainViewModel

//    override fun getViewModel(): MainViewModel {
//        return model
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = findViewById<View>(R.id.list_view_repos) as ListView
        listView.adapter = adapter
        val editTextUsername = findViewById<View>(R.id.edit_text_username) as EditText
        val buttonSearch = findViewById<View>(R.id.button_search) as Button
        val inProgress = findViewById<View>(R.id.progressBar) as ProgressBar

        val model = ViewModelProviders.of(this).get(MainViewModel::class.java)

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
