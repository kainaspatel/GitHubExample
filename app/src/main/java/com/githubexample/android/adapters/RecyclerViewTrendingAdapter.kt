package com.githubexample.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubexample.android.R
import com.githubexample.android.listeners.RecyclerViewClickListener
import com.githubexample.android.models.Repo
import com.githubexample.android.models.TrendingRepoModel
import kotlinx.android.synthetic.main.row_repo.view.*

class RecyclerViewTrendingAdapter(private val mContext: Context, private val recyclerViewClickListener: RecyclerViewClickListener)
    : RecyclerView.Adapter<RecyclerViewTrendingAdapter.OrderViewHolder>() {

    private val mArrayListTrending: ArrayList<Repo> = ArrayList()
    private var type: String? = null


    fun setListTrending(arrayList: ArrayList<Repo>?) {
        this.mArrayListTrending.clear()
        this.mArrayListTrending.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_repo, parent, false)

        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mArrayListTrending.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(mArrayListTrending[position])
    }

    inner class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repoModel: Repo) {

            itemView.tvId.text = "Id : ${repoModel.id}"
            itemView.tvName.text = "Name : ${repoModel.name}"
            itemView.tvStart.text = "Star : ${repoModel.stargazers_count}"
            itemView.tvOwner.text = "Owner : ${repoModel.owner?.login}"

            itemView.setOnClickListener {
                recyclerViewClickListener.onItemClickedTrending(adapterPosition ,repoModel)
            }
        }

    }
}