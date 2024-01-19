package com.example.secondapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import model_kotlin.ModelView
import model_kotlin.User

class UpdatePersonActivity : AppCompatActivity() {
    private fun selectSpinnerValue(spinner: Spinner, value: String) {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == value) {
                spinner.setSelection(i)
                break
            }
        }
    }
    fun initSpinner(userSexSpinner: Spinner, userGroupSpinner: Spinner, userCitySpinner: Spinner,
                    spinnerSexValue: String, spinnerGroupValue: String, spinnerCityValue: String) {
        val sex_list = ModelView().getAllSex().map { it.value }
        val groups_list = ModelView().getAllGroups().map { it.name }
        val cities_list = ModelView().getAllCities().map { it.name }

        val sex_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sex_list)
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userSexSpinner.adapter = sex_adapter
        selectSpinnerValue(userSexSpinner, spinnerSexValue)

        val groups_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, groups_list)
        groups_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userGroupSpinner.adapter = groups_adapter
        selectSpinnerValue(userGroupSpinner, spinnerGroupValue)

        val cities_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities_list)
        cities_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        userCitySpinner.adapter = cities_adapter
        selectSpinnerValue(userCitySpinner, spinnerCityValue)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_person)

        val intentUserId = intent.getIntExtra("userId", 0)
        val intentUserName = intent.getStringExtra("userName").toString()
        val intentUserSex = intent.getStringExtra("userSex").toString()
        val intentUserSexId = intent.getIntExtra("userSexId", 1)
        val intentUserGroup = intent.getStringExtra("userGroup").toString()
        val intentUserGroupId = intent.getIntExtra("userGroupId", 1)
        val intentUserCity = intent.getStringExtra("userCity").toString()
        val intentUserCityId = intent.getIntExtra("userCityId", 1)

        println("AAAAAAAAAAAAAAAAAAAAaa0  ${intent.extras}")
        println("AAAAAAAAAAAAAAAAAAAAaa1  ${intentUserSexId}")
        println("AAAAAAAAAAAAAAAAAAAAaa2  ${intentUserGroupId}")
        println("AAAAAAAAAAAAAAAAAAAAaa3  ${intentUserCityId}")

        val userId: TextView = findViewById(R.id.updateUser_userId)
        userId.text = intentUserId.toString()

        val userName: EditText = findViewById(R.id.updateUser_name)
        userName.setText(intentUserName)

        val userSexSpinner: Spinner = findViewById(R.id.updateUser_spinnerUserSex)
        val userGroupSpinner: Spinner = findViewById(R.id.updateUser_spinnerUserGroup)
        val userCitySpinner: Spinner = findViewById(R.id.updateUser_spinnerUserCity)
        this.initSpinner(userSexSpinner, userGroupSpinner, userCitySpinner, intentUserSex, intentUserGroup, intentUserCity)

        val updateButton: Button = findViewById(R.id.updateUser_updateButton)
        val backButton: Button = findViewById(R.id.updateUser_backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, PersonsActivity::class.java)
            startActivity(intent)
        }

        updateButton.setOnClickListener {
            if (userName.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userData_userId = ModelView().getAllUsers().size+1
            val userData_userName = userName.text.toString().trim()
            val userData_userSex = userSexSpinner.selectedItem.toString()
            val userData_userGroup = userGroupSpinner.selectedItem.toString()
            val userData_userCity = userCitySpinner.selectedItem.toString()


            val user = User(userData_userId, userData_userName,
                ModelView().getSexByName(userData_userSex),
                ModelView().getGroupByName(userData_userGroup),
                ModelView().getCityByName(userData_userCity))

            ModelView().addUser(user)

            Toast.makeText(this, "Пользователь ${user.name} успешно добавлен",
                Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PersonsActivity::class.java)
            startActivity(intent)
        }
        
    }
}