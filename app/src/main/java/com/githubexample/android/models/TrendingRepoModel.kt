package com.githubexample.android.models

import com.google.gson.annotations.SerializedName

class TrendingRepoModel {
    var author: String? = null
    var language: String? = null
    var stars: Int = 0
    var forks: Int = 0
    var currentPeriodStars: Int = 0

    var id: Int = 0
    var node_id: String? = null
    var name: String? = null
    var full_name: String? = null
    var owner: OwnerBean? = null
    @SerializedName("private")
    var isPrivateX: Boolean = false
    var html_url: String? = null
    var description: String? = null
    var isFork: Boolean = false
    var url: String? = null
    var forks_url: String? = null
    var keys_url: String? = null
    var collaborators_url: String? = null
    var teams_url: String? = null
    var hooks_url: String? = null
    var issue_events_url: String? = null
    var events_url: String? = null
    var assignees_url: String? = null
    var branches_url: String? = null
    var tags_url: String? = null
    var blobs_url: String? = null
    var git_tags_url: String? = null
    var git_refs_url: String? = null
    var trees_url: String? = null
    var statuses_url: String? = null
    var languages_url: String? = null
    var stargazers_url: String? = null
    var contributors_url: String? = null
    var subscribers_url: String? = null
    var subscription_url: String? = null
    var commits_url: String? = null
    var git_commits_url: String? = null
    var comments_url: String? = null
    var issue_comment_url: String? = null
    var contents_url: String? = null
    var compare_url: String? = null
    var merges_url: String? = null
    var archive_url: String? = null
    var downloads_url: String? = null
    var issues_url: String? = null
    var pulls_url: String? = null
    var milestones_url: String? = null
    var notifications_url: String? = null
    var labels_url: String? = null
    var releases_url: String? = null
    var deployments_url: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var pushed_at: String? = null
    var git_url: String? = null
    var ssh_url: String? = null
    var clone_url: String? = null
    var svn_url: String? = null
    var homepage: String? = null
    var size: Int = 0
    var stargazers_count: Int = 0
    var watchers_count: Int = 0
    var isHas_issues: Boolean = false
    var isHas_projects: Boolean = false
    var isHas_downloads: Boolean = false
    var isHas_wiki: Boolean = false
    var isHas_pages: Boolean = false
    var forks_count: Int = 0
    var mirror_url: Any? = null
    var isArchived: Boolean = false
    var open_issues_count: Int = 0
    var license: Any? = null
    var open_issues: Int = 0
    var watchers: Int = 0
    var default_branch: String? = null
    var permissions: PermissionsBean? = null

    class OwnerBean {
        var login: String? = null
        var id: Int = 0
        var node_id: String? = null
        var avatar_url: String? = null
        var gravatar_id: String? = null
        var url: String? = null
        var html_url: String? = null
        var followers_url: String? = null
        var following_url: String? = null
        var gists_url: String? = null
        var starred_url: String? = null
        var subscriptions_url: String? = null
        var organizations_url: String? = null
        var repos_url: String? = null
        var events_url: String? = null
        var received_events_url: String? = null
        var type: String? = null
        var isSite_admin: Boolean = false
    }

    class PermissionsBean {
        var isAdmin: Boolean = false
        var isPush: Boolean = false
        var isPull: Boolean = false
    }
}
