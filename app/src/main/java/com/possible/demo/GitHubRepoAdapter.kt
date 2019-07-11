package com.possible.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.possible.demo.model.GithubRepo
import com.squareup.picasso.Picasso


class GitHubRepoAdapter: BaseAdapter() {
    private var gitHubRepos: List<GithubRepo> = ArrayList()

    override fun getCount(): Int = gitHubRepos.size

    override fun getItem(position: Int): GithubRepo? {
        return if(position < 0 || position >= gitHubRepos.size) {
            return null
        } else {
            gitHubRepos[position]
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View = convertView ?: createView(parent)
        val viewHolder = view.tag as GitHubRepoViewHolder
        viewHolder.setGitHubRepo(getItem(position), parent.context)
        return view
    }

    private fun createView(parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_item_github_repo, parent, false)
        val viewHolder = GitHubRepoViewHolder(view)
        view.tag = viewHolder
        return view
    }

    fun setGitHubRepos(repos: List<GithubRepo>) {
        clearList()
        gitHubRepos = repos
        notifyDataSetChanged()
    }

    private fun clearList() {
        gitHubRepos = listOf()
        notifyDataSetChanged()
    }

    class GitHubRepoViewHolder(view: View) {
        private var repoAvatar: ImageView = view.findViewById(R.id.follower_icon)
        private var textName: TextView = view.findViewById(R.id.text_login_name)

        fun setGitHubRepo(gitHubRepo: GithubRepo?, context: Context) {
            Picasso.with(context).load(gitHubRepo?.avatar_url).into(repoAvatar)
            textName.text = gitHubRepo?.login
        }
    }
}