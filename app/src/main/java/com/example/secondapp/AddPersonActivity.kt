package com.example.secondapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import model_kotlin.ModelView
import model_kotlin.User
import model_kotlin.user_city
import model_kotlin.user_group
import model_kotlin.user_sex

class AddPersonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person)

        val userName: EditText = findViewById(R.id.addUser_name)
        val addButton: Button = findViewById(R.id.addUser_addButton)
        val backButton: Button = findViewById(R.id.addUser_backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, PersonsActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            if (userName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val number_of_users = ModelView().getAllUsers().size+1
            val user = User(number_of_users, userName.text.toString().trim()
                , user_sex(1, "М"),
                user_group(1, "Белые"),
                user_city(1, "Москва"))
            ModelView().addUser(user)

            Toast.makeText(this, "Пользователь ${user.name} успешно добавлен",
                Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PersonsActivity::class.java)
            startActivity(intent)

        }



    }
}