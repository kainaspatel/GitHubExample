package com.githubexample.android.listeners

import com.githubexample.android.models.Repo
import com.githubexample.android.models.RepoModel
import com.githubexample.android.models.TrendingRepoModel

interface RecyclerViewClickListener {
    fun onItemClicked(pos: Int, repoModel: RepoModel )

    fun onItemClickedTrending(pos: Int,repoTrendingModel: Repo)

}