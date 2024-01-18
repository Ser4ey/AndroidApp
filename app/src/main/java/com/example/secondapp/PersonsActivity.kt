package com.example.secondapp

import android.annotation.SuppressLint
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

class PersonsActivity : AppCompatActivity() {

    val a = "23"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)

//        val list_of_items = arrayOf("Item 1", "Item 2", "Item 3")
//
//        val spinner = findViewById<Spinner>(R.id.spinner)
//
//        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = aa
//
//        spinner.selectedItem.toString()

        val persons = ModelView().getAllUsers()
        val personsList: RecyclerView = findViewById(R.id.personsList)

        personsList.layoutManager = LinearLayoutManager(this)
        personsList.adapter = PersonsAdapter(persons, this)


        val sortedOption = findViewById<Button>(R.id.sortButton)
        sortedOption.setOnClickListener {
            if (sortedOption.text.toString() == "Нет") {
                sortedOption.text = "A -> Я"
                personsList.adapter = PersonsAdapter(ModelView().getAllSortedUsers(true), this)
            } else if (sortedOption.text.toString() == "A -> Я") {
                sortedOption.text = "Я -> A"
                personsList.adapter = PersonsAdapter(ModelView().getAllSortedUsers(false), this)
            } else {
                sortedOption.text = "Нет"
                personsList.adapter = PersonsAdapter(ModelView().getAllUsers(), this)
            }

        }





    }
}



