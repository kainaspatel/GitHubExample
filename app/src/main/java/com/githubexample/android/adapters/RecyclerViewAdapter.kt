package com.githubexample.android.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubexample.android.R
import com.githubexample.android.listeners.RecyclerViewClickListener
import com.githubexample.android.models.RepoModel
import com.githubexample.android.models.TrendingRepoModel
import kotlinx.android.synthetic.main.row_repo.view.*

class RecyclerViewAdapter(private val mContext: Context, private val recyclerViewClickListener: RecyclerViewClickListener)
    : RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder>() {

    private val mArrayList: ArrayList<RepoModel> = ArrayList()
    private var type: String? = null

    fun setList(arrayList: ArrayList<RepoModel>?) {
        this.mArrayList.clear()
        this.mArrayList.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_repo, parent, false)

        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(mArrayList[position])
    }

    inner class OrderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repoModel: RepoModel) {

            itemView.tvId.text = "Id : ${repoModel.id}"
            itemView.tvName.text = "Name : ${repoModel.name}"
            itemView.tvStart.text = "Star : ${repoModel.stargazers_count}"
            itemView.tvOwner.text = "Owner : ${repoModel.owner?.login}"

            itemView.setOnClickListener {
                recyclerViewClickListener.onItemClicked(adapterPosition, repoModel )
            }

        }

    }
}