package com.ddamn.yevm_examen_2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ddamn.yevm_examen_2.R
import com.ddamn.yevm_examen_2.entities.Comment

class AdapterComment(var listadoComments: List<Comment>):
    RecyclerView.Adapter<AdapterComment.ViewHolder>() {
    private lateinit var miListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(comment: Comment)
        fun onItemLongClick(comment: Comment)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        miListener = listener
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        val nameComment : TextView = itemView.findViewById(R.id.tvNameRecyclerView)
        var emailComment: TextView = itemView.findViewById(R.id.tvEmailRecyclerView)
        val bodyComment : TextView = itemView.findViewById(R.id.tvBodyRecyclerView)

        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoComments[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoComments[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista: View = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = listadoComments[position]

        holder.nameComment.text = comment.name
        holder.emailComment.text = comment.email
        holder.bodyComment.text = comment.body
    }

    override fun getItemCount() = listadoComments.size

    fun updateCommentList(newCommentList: List<Comment>) {
        newCommentList.forEach {
            Log.d("Comment", "Adaptador Comment: ${it}.")
        }
        listadoComments = newCommentList
        notifyDataSetChanged()
    }
}