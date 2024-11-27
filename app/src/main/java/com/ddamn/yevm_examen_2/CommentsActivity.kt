package com.ddamn.yevm_examen_2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddamn.yevm_examen_2.adapters.AdapterComment
import com.ddamn.yevm_examen_2.entities.Comment
import com.ddamn.yevm_examen_2.repositories.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsActivity : AppCompatActivity() {
    private val commentRepository = CommentRepository()
    lateinit var recyclerViewComments: RecyclerView

    companion object {
        var adaptadorRecyler: AdapterComment = AdapterComment(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewComments = findViewById(R.id.rvComments)
        recyclerViewComments.layoutManager = LinearLayoutManager(this)
        recyclerViewComments.adapter = CommentsActivity.adaptadorRecyler

        val id = intent.getLongExtra("id_post", -1L)

        if(id != -1L){
            obtenerComments(id)
        }

        CommentsActivity.adaptadorRecyler.setOnItemClickListener(object :
            AdapterComment.onItemClickListener {
            override fun onItemClick(comment: Comment) {
            }
            override fun onItemLongClick(comment: Comment) {
            }
        })
    }

    private fun obtenerComments(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val comment = commentRepository.getAllComments(id)
                withContext(Dispatchers.Main) {
                    CommentsActivity.adaptadorRecyler.updateCommentList(comment)
                    Toast.makeText(this@CommentsActivity, "Mostrando Comentarios", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CommentsActivity, "ERROR", Toast.LENGTH_SHORT).show()
                Log.d("Users", "Error al obtener users ${e.message}")
                if (e is retrofit2.HttpException && e.code() == 404) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@CommentsActivity,
                            "Aún no hay comentarios",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@CommentsActivity,
                            "Error al comentarios datos: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}