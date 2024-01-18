package com.example.secondapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button: Button = findViewById(R.id.button_login)

        button.setOnClickListener{
            val intent = Intent(this, PersonsActivity::class.java)
            startActivity(intent)
        }

//        button.setOnClickListener {
//            val login = userLogin.text.toString().trim()
//            val email = userEmail.text.toString().trim()
//            val pass = userPass.text.toString().trim()
//
//            if (login == "" || email == "" || pass == "")
//                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_LONG).show()
//            else {
//                val user1 = User1(login, email, pass)
//
//                val db = DbHelper(this, null)
//                db.addUser(user1)
//                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
//
//                userLogin.text.clear()
//                userEmail.text.clear()
//                userPass.text.clear()
//            }
//        }


    }
}