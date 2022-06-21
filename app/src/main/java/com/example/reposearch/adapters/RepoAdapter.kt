package com.example.reposearch.adapters

import com.example.reposearch.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reposearch.data.Repo
import com.example.reposearch.ui.RecyclerViewClickListener

class RepoAdapter(private val listener: RecyclerViewClickListener) : RecyclerView.Adapter<RepoAdapter.RepoVH>() {

    class RepoVH(parentView: View) : RecyclerView.ViewHolder(parentView) {
        var txtDescription: TextView = parentView.findViewById(R.id.repo_description)
        var txtStars: TextView = parentView.findViewById(R.id.txt_stars)
        var btnToGo: ImageView = parentView.findViewById(R.id.btn_go_to)
        var txtNameRepo: TextView = parentView.findViewById(R.id.txt_name_repo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RepoVH(LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false))

    override fun getItemCount() = diff.currentList.size

    override fun onBindViewHolder(holder: RepoVH, position: Int) {
        holder.txtNameRepo.text = diff.currentList[position].name.uppercase()
        holder.txtDescription.text = diff.currentList[position].description
        holder.txtStars.text = diff.currentList[position].stargazers_count.toString()
        holder.btnToGo.setOnClickListener {
            listener.onRecyclerViewItemClick(diff.currentList[position].html_url)
        }
    }

    private val callback = object : DiffUtil.ItemCallback<Repo>(){
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem.description == newItem.description
    }

    val diff = AsyncListDiffer(this, callback)

}
