package com.possible.demo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.possible.demo.model.GithubRepo
import com.squareup.picasso.Picasso


class GitHubRepoAdapter: RecyclerView.Adapter<GitHubRepoAdapter.ViewHolder>() {

    private var gitHubFollowerList: List<GithubRepo> = ArrayList()
    private var context: Context?= null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_github_repo, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = gitHubFollowerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("Follower Size: " + gitHubFollowerList.size)
        holder.bindItems(gitHubFollowerList[position], context)
    }

    private fun clearList() {
        gitHubFollowerList = listOf()
        notifyDataSetChanged()
    }

    fun setGitHubRepos(repos: List<GithubRepo>) {
        clearList()
        gitHubFollowerList = repos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var repoAvatar: ImageView = itemView.findViewById(R.id.follower_icon)
        private var textName: TextView = itemView.findViewById(R.id.text_login_name)

        fun bindItems(gitHubRepo: GithubRepo?, context: Context?) {
            Picasso.with(context).load(gitHubRepo?.avatar_url).into(repoAvatar)
            textName.text = gitHubRepo?.login
        }
    }
}
