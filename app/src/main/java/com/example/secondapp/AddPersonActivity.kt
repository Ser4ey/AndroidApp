package com.example.secondapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import model_kotlin.ModelView
import model_kotlin.User
import model_kotlin.user_city
import model_kotlin.user_group
import model_kotlin.user_sex

class AddPersonActivity : AppCompatActivity() {
    fun initSpinner(userSexSpinner: Spinner, userGroupSpinner: Spinner, userCitySpinner: Spinner) {
        val sex_list = ModelView().getAllSex().map { it.value }
        val groups_list = ModelView().getAllGroups().map { it.name }
        val cities_list = ModelView().getAllCities().map { it.name }

        val sex_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sex_list)
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userSexSpinner.adapter = sex_adapter

        val groups_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, groups_list)
        groups_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userGroupSpinner.adapter = groups_adapter

        val cities_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities_list)
        cities_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userCitySpinner.adapter = cities_adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_person)

        val userName: EditText = findViewById(R.id.addUser_name)
        val userSexSpinner: Spinner = findViewById(R.id.addUser_spinnerUserSex)
        val userGroupSpinner: Spinner = findViewById(R.id.addUser_spinnerUserGroup)
        val userCitySpinner: Spinner = findViewById(R.id.addUser_spinnerUserCity)
        this.initSpinner(userSexSpinner, userGroupSpinner, userCitySpinner)

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


//        val list_of_items = arrayOf("Item 1", "Item 2", "Item 3")
//
//        val spinner = findViewById<Spinner>(R.id.addUser_spinnerUserGroup)
//        val spinner2 = findViewById<Spinner>(R.id.addUser_spinnerUserSex)
//
//        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = aa
//        spinner2.adapter = aa

//        spinner.selectedItem.toString()

    }
}