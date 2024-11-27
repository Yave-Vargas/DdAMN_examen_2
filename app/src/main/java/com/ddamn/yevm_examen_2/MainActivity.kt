package com.ddamn.yevm_examen_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnUsers = findViewById<Button>(R.id.btnUsers)
        val btnCreditos = findViewById<Button>(R.id.btnCreditos)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        btnUsers.setOnClickListener {
            val intentRecycler = Intent(this, UsersActivity::class.java)
            startActivity(intentRecycler)
        }
        btnCreditos.setOnClickListener {
            val intentCreditos = Intent(this, CreditosActivity::class.java)
            startActivity(intentCreditos)
        }
        btnSalir.setOnClickListener {
            finishAffinity() // Cierra todas las actividades
        }

    }
}