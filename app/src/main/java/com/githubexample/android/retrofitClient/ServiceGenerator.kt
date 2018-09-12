package com.githubexample.android.retrofitClient

import com.githubexample.android.models.Repo
import com.githubexample.android.models.RepoContent
import com.githubexample.android.models.RepoModel
import com.githubexample.android.models.TrendingRepoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ServiceGenerator {

//    @FormUrlEncoded
//    @POST(signIn)
//    fun getLogin(@FieldMap map: HashMap<String, String>): Call<LoginModel>


    @GET(getUserRepo)
    fun getRepos(@Header("Authorization") authHeader: String): Call<ArrayList<RepoModel>>

    //trending
    @GET("http://trending.codehub-app.com/v2/trending")
    fun getTrendingRepo(@Query("language") language: String, @Query("since") since: String):
            Call<ArrayList<Repo>>


    @GET("repos/{owner}/{repo}/readme")
    fun getReadme(@Path("owner") owner: String, @Path("repo") repo: String):
            Call<RepoContent>

}