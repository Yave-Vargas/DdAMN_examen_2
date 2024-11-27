package com.ddamn.yevm_examen_2

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ddamn.yevm_examen_2.adapters.AdapterUser
import com.ddamn.yevm_examen_2.data.UserDatabase
import com.ddamn.yevm_examen_2.entities.UserEntity
import com.ddamn.yevm_examen_2.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersActivity : AppCompatActivity() {
    private val userRepository = UserRepository()
    private var escucharDatosLocales = false
    lateinit var recyclerViewUsers: RecyclerView
    lateinit var db: UserDatabase

    companion object {
        var adaptadorRecyler: AdapterUser = AdapterUser(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = UserDatabase.getDatabase(this)

        recyclerViewUsers = findViewById(R.id.rvUsers)
        recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        recyclerViewUsers.adapter = adaptadorRecyler

        obtenerUsers()

        adaptadorRecyler.setOnItemClickListener(object :
            AdapterUser.onItemClickListener {
            override fun onItemClick(user: UserEntity) {
                Log.d("Users", "Clic corto, Users ${user.name}}")
                val intent = Intent(this@UsersActivity, PostsActivity::class.java)
                intent.putExtra("id_user", user.id)
                startActivity(intent)
            }
            override fun onItemLongClick(user: UserEntity) {
                Log.d("Users", "Clic largo: Users ${user.name}")
                guardarUser(user)
            }
        })
    }

    private fun obtenerUsers() {
        if (hayConexion()) {
            escucharDatosLocales = false // No cargar datos locales si hay internet
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val users = userRepository.getAllUsers()
                    withContext(Dispatchers.Main) {
                        adaptadorRecyler.updateList(users)
                        Toast.makeText(this@UsersActivity, "Mostrando Usuarios", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.d("Users", "Error al obtener users ${e.message}")
                    if (e is retrofit2.HttpException && e.code() == 404) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UsersActivity,
                                "Aún no hay Users",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UsersActivity,
                                "Error al obtener datos: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } else {
            escucharDatosLocales = true // Cargar datos locales si hay internet
            CoroutineScope(Dispatchers.IO).launch {
                db.UserDao().getAllUsers().collect { usuariosLocales ->
                    if (escucharDatosLocales) {
                        withContext(Dispatchers.Main) {
                            adaptadorRecyler.updateList(usuariosLocales)
                            Toast.makeText(this@UsersActivity, "Mostrando datos locales", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun guardarUser(user: UserEntity) {
        val idUser = user.id
        val nameUser = user.name
        val userNameUser = user.username
        val emailUser = user.email

        try {
            GlobalScope.launch {
                db.UserDao().add(
                    UserEntity(
                        id = idUser,
                        name = nameUser,
                        username = userNameUser,
                        email = emailUser
                    )
                )
            }
            Toast.makeText(this@UsersActivity, "Usuario guardado en la base de datos", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this@UsersActivity, "Error $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hayConexion(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}