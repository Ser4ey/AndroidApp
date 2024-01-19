package com.example.secondapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model_kotlin.ModelView
import model_kotlin.users

class PersonsActivity : AppCompatActivity() {
    var currentPersons = ModelView().getAllUsers()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)
        this.currentPersons = ModelView().getAllUsers()

        val personsList: RecyclerView = findViewById(R.id.personsList)
        personsList.layoutManager = LinearLayoutManager(this)
        personsList.adapter = PersonsAdapter(this.currentPersons, this)

        val sortedOption = findViewById<Button>(R.id.sortButton)
        sortedOption.setOnClickListener {
            if (sortedOption.text.toString() == "Нет") {
                sortedOption.text = "A -> Я"
                personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}, this)
            } else if (sortedOption.text.toString() == "A -> Я") {
                sortedOption.text = "Я -> A"
                personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}.reversed(), this)
            } else {
                sortedOption.text = "Нет"
                personsList.adapter = PersonsAdapter(this.currentPersons, this)
            }
        }


        val buttonAddPerson = findViewById<Button>(R.id.buttonAddPerson)
        buttonAddPerson.setOnClickListener {
            val intent = Intent(this, AddPersonActivity::class.java)
            startActivity(intent)
        }






    }
}



