package com.ddamn.yevm_examen_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddamn.yevm_examen_2.adapters.AdapterPost
import com.ddamn.yevm_examen_2.entities.Post
import com.ddamn.yevm_examen_2.repositories.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsActivity : AppCompatActivity() {
    private val postRepository = PostRepository()
    lateinit var recyclerViewPosts: RecyclerView

    companion object {
        var adaptadorRecyler: AdapterPost = AdapterPost(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewPosts = findViewById(R.id.rvPosts)
        recyclerViewPosts.layoutManager = LinearLayoutManager(this)
        recyclerViewPosts.adapter = PostsActivity.adaptadorRecyler

        val id = intent.getLongExtra("id_user", -1L)

        if(id != -1L){
            obtenerPosts(id.toLong())
        }

        PostsActivity.adaptadorRecyler.setOnItemClickListener(object :
            AdapterPost.onItemClickListener {
            override fun onItemLongClick(post: Post) {
                //Log.d("Users", "Clic largo: Users ${post.title}")
            }
            override fun onItemClick(post: Post) {
                //Log.d("Posts", "Clic corto, Users ${post.title}}")
                val intent = Intent(this@PostsActivity, CommentsActivity::class.java)
                intent.putExtra("id_post", post.id)
                startActivity(intent)
            }
        })
    }

    private fun obtenerPosts(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val posts = postRepository.getAllPosts(id)
                withContext(Dispatchers.Main) {
                    PostsActivity.adaptadorRecyler.updatePostList(posts)
                    Toast.makeText(this@PostsActivity, "Mostrando Posts", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PostsActivity, "ERROR", Toast.LENGTH_SHORT).show()
                Log.d("Users", "Error al obtener users ${e.message}")
                if (e is retrofit2.HttpException && e.code() == 404) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@PostsActivity,
                            "Aún no hay Posts",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@PostsActivity,
                            "Error al obtener posts: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}