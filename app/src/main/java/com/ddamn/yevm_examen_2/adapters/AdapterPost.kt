package com.ddamn.yevm_examen_2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ddamn.yevm_examen_2.R
import com.ddamn.yevm_examen_2.entities.Post

class AdapterPost(var listadoPosts: List<Post>):
    RecyclerView.Adapter<AdapterPost.ViewHolder>() {
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(post: Post)
        fun onItemLongClick(post: Post)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val titlePost : TextView = itemView.findViewById(R.id.tvTitleRecyclerView)
        var bodyPost: TextView = itemView.findViewById(R.id.tvBodyRecyclerView)
        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoPosts[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoPosts[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista: View = LayoutInflater.from(parent.context).inflate(R.layout.posts_item, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = listadoPosts[position]
        holder.titlePost.text = post.title
        holder.bodyPost.text = post.body
    }

    override fun getItemCount() = listadoPosts.size


    fun updatePostList(newPostList: List<Post>) {
        newPostList.forEach {
            Log.d("Cat", "Adaptador Post: ${it}.")
        }
        listadoPosts = newPostList
        notifyDataSetChanged()
    }
}