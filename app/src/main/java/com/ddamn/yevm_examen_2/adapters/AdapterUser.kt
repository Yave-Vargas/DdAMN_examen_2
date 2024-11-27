package com.ddamn.yevm_examen_2.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ddamn.yevm_examen_2.R
import com.ddamn.yevm_examen_2.entities.UserEntity

class AdapterUser(var listadoUsers: List<UserEntity>) :
    RecyclerView.Adapter<AdapterUser.ViewHolder>() {

    private lateinit var miListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(user: UserEntity)
        fun onItemLongClick(user: UserEntity)
    }

    fun setOnItemClickListener(listener: AdapterUser.onItemClickListener) {
        miListener = listener
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        var nameUser: TextView = itemView.findViewById(R.id.tvNameRecyclerView)
        val userNameUser: TextView = itemView.findViewById(R.id.tvUserNameRecyclerView)
        val emailUser: TextView = itemView.findViewById(R.id.tvemailRecyclerView)

        init {
            itemView.setOnClickListener {
                miListener.onItemClick(listadoUsers[adapterPosition])
            }
            itemView.setOnLongClickListener {
                miListener.onItemLongClick(listadoUsers[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(vista, miListener)
    }

    override fun getItemCount() = listadoUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listadoUsers[position]
        holder.nameUser.text = user.name
        holder.userNameUser.text = user.username
        holder.emailUser.text = user.email
    }

    fun updateList(newUsersList: List<UserEntity>) {
        newUsersList.forEach {
            Log.d("Cat", "Adaptador User: ${it}.")
        }
        listadoUsers = newUsersList
        notifyDataSetChanged()
    }
}