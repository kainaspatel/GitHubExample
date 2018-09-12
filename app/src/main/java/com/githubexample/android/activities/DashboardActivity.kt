package com.githubexample.android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.githubexample.android.R
import com.githubexample.android.adapters.RecyclerViewAdapter
import com.githubexample.android.adapters.RecyclerViewTrendingAdapter
import com.githubexample.android.listeners.RecyclerViewClickListener
import com.githubexample.android.models.Repo
import com.githubexample.android.models.RepoContent
import com.githubexample.android.models.RepoModel
import com.githubexample.android.models.TrendingRepoModel
import com.githubexample.android.retrofitClient.RetrofitClientSingleton
import com.githubexample.android.utils.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_dashboard.*
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Response

class DashboardActivity : AppCompatActivity(), View.OnClickListener, RecyclerViewClickListener {
    val cssFile = "file:///android_asset/markdown_css_themes/classic.css"
    val format = "UTF-8"


    val recyclerViewAdapter by lazy {
        RecyclerViewAdapter(this, this) }


    val recyclerViewTrendingAdapter by lazy {
        RecyclerViewTrendingAdapter(this, this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        btnGetUsersRepo.setOnClickListener(this)
        btnGetTrendingRepo.setOnClickListener(this)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerViewAdapter

        recyclerViewTrending.layoutManager = LinearLayoutManager(this)
        recyclerViewTrending.adapter = recyclerViewTrendingAdapter

        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        })

    }


    private fun getRepos() {
        recyclerView.visibility = View.VISIBLE
        recyclerViewTrending.visibility = View.GONE
        webView.visibility = View.GONE

        showProgressDialog(this@DashboardActivity, btnGetUsersRepo as View)

        val username = etEmail.text.toString()
        val password = etPassword.text.toString()
        RetrofitClientSingleton.getInstance().getRepos(Credentials.basic(username, password))
                .enqueue(object : retrofit2.Callback<ArrayList<RepoModel>> {
                    override fun onFailure(call: Call<ArrayList<RepoModel>>, t: Throwable) {
                        stopProgress()
                        toast(this@DashboardActivity, getString(R.string.something_went_wrong))
                    }

                    override fun onResponse(call: Call<ArrayList<RepoModel>>, response: Response<ArrayList<RepoModel>>) {
                        stopProgress()
                        if (response.isSuccessful) {

                            if (response.body() != null) {
                                recyclerView.hideKeyboard()
                                recyclerViewAdapter.setList(response.body()!!)
                            }
                        } else {
                            toast(this@DashboardActivity, R.string.something_went_wrong)
                        }
                    }
                })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnGetUsersRepo -> getRepos()
            R.id.btnGetTrendingRepo -> getTrendingRepo()
        }
    }

    private fun getTrendingRepo() {
        recyclerView.visibility = View.GONE
        recyclerViewTrending.visibility = View.VISIBLE
        webView.visibility = View.GONE

        showProgressDialog(this@DashboardActivity, btnGetUsersRepo as View)
        RetrofitClientSingleton.getInstance().getTrendingRepo("","")
                .enqueue(object : retrofit2.Callback<ArrayList<Repo>> {
                    override fun onFailure(call: Call<ArrayList<Repo>>, t: Throwable) {
                        stopProgress()
                        toast(this@DashboardActivity, getString(R.string.something_went_wrong))
                    }

                    override fun onResponse(call: Call<ArrayList<Repo>>, response: Response<ArrayList<Repo>>) {
                        stopProgress()
                        if (response.isSuccessful) {

                            if (response.body() != null) {
                                recyclerView.hideKeyboard()
                                recyclerViewTrendingAdapter.setListTrending(response.body()!!)
                                toast(this@DashboardActivity, "success")

                            }
                        } else {
                            toast(this@DashboardActivity, R.string.something_went_wrong)
                        }
                    }
                })
    }

    override fun onItemClicked(pos: Int, repoModel: RepoModel) {
        toast(this, "Item Clicked : ${repoModel.name}")
    }

    override fun onItemClickedTrending(pos: Int, repoTrendingModel: Repo) {
        toast(this, "Item Clicked : ${repoTrendingModel.name}")
       getReadme(repoTrendingModel.owner.getLogin(), repoTrendingModel.name ,repoTrendingModel.html_url)
    }



    private fun getReadme(owner_login : String , name_repo : String ,html_url : String) {

        recyclerView.visibility = View.GONE
        recyclerViewTrending.visibility = View.GONE
        webView.visibility = View.VISIBLE

        showProgressDialog(this@DashboardActivity, btnGetUsersRepo as View)
        RetrofitClientSingleton.getInstance().getReadme(owner_login,name_repo)
                .enqueue(object : retrofit2.Callback<RepoContent> {
                    override fun onFailure(call: Call<RepoContent>, t: Throwable) {
                        stopProgress()
                        toast(this@DashboardActivity, getString(R.string.something_went_wrong))
                    }

                    override fun onResponse(call: Call<RepoContent>, response: Response<RepoContent>) {
                        stopProgress()
                        var fmt: String? = null

                        if (response.isSuccessful) {

                            if (response.body() != null) {
                                recyclerView.hideKeyboard()
                            //    markdown = String(EncodingUtil.fromBase64(repoContent.getContent()), "UTF-8")
                                toast(this@DashboardActivity, "success")
                               // webView.loadDataWithBaseURL(null, html_url, "text/html", "UTF-8", null)
                                webView!!.loadUrl(html_url)
                                //webView.loadDataWithBaseURL(null, loadMarkdownToHtml(String(fromBase64(response.body().toString()),"UTF-8"), cssFile), "text/html", "UTF-8", null)
                            }
                        } else {
                            toast(this@DashboardActivity, R.string.something_went_wrong)
                        }
                    }
                })
    }

    fun fromBase64(content: String): ByteArray {
        return Base64.decode(content, Base64.DEFAULT)
    }


    private fun loadMarkdownToHtml(txt: String, cssFile: String?): String? {
        val html: String
        if (null != cssFile) {
            html = "<link rel='stylesheet' type='text/css' href='$cssFile' />$txt"
            return html
        }
        return null
    }


}
