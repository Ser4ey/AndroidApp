//package com.example.secondapp
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.View
//import android.widget.AdapterView
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.CheckBox
//import android.widget.Spinner
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import model_kotlin.ModelView
//import model_kotlin.User
//import model_kotlin.user_city
//import model_kotlin.user_group
//import model_kotlin.user_sex
//import model_kotlin.users
//
//class PersonsActivity : AppCompatActivity() {
//    var currentPersons = ModelView().getAllUsers()
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_persons)
//        this.currentPersons = ModelView().getAllUsers()
//
//        val personsList: RecyclerView = findViewById(R.id.personsList)
//        personsList.layoutManager = LinearLayoutManager(this)
//        personsList.adapter = PersonsAdapter(this.currentPersons, this)
//
//        val sortedOption = findViewById<Button>(R.id.sortButton)
//        sortedOption.setOnClickListener {
//            if (sortedOption.text.toString() == "Нет") {
//                sortedOption.text = "A -> Я"
//                personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}, this)
//            } else if (sortedOption.text.toString() == "A -> Я") {
//                sortedOption.text = "Я -> A"
//                personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}.reversed(), this)
//            } else {
//                sortedOption.text = "Нет"
//                personsList.adapter = PersonsAdapter(this.currentPersons, this)
//            }
//        }
//
//        val buttonAddPerson = findViewById<Button>(R.id.buttonAddPerson)
//        buttonAddPerson.setOnClickListener {
//            val intent = Intent(this, AddPersonActivity::class.java)
//            startActivity(intent)
//        }
//
//        val filterCheckBoxSex1 = findViewById<CheckBox>(R.id.filterCheckBoxSex1)
//        val filterCheckBoxSex2 = findViewById<CheckBox>(R.id.filterCheckBoxSex2)
//        val filterCheckBoxGroup1 = findViewById<CheckBox>(R.id.filterCheckBoxGroup1)
//        val filterCheckBoxGroup2 = findViewById<CheckBox>(R.id.filterCheckBoxGroup2)
//        val filterCheckBoxCity1 = findViewById<CheckBox>(R.id.filterCheckBoxCity1)
//        val filterCheckBoxCity2 = findViewById<CheckBox>(R.id.filterCheckBoxCity2)
//
//
//        val checkBoxOnClickListener = View.OnClickListener { _ ->
//            run {
//                val filterBySex = mutableListOf<user_sex>()
//                val filterByGroup = mutableListOf<user_group>()
//                val filterByCities = mutableListOf<user_city>()
//
//                if (filterCheckBoxSex1.isChecked())
//                    filterBySex.add(ModelView().getSexByName("М"))
//                if (filterCheckBoxSex2.isChecked())
//                    filterBySex.add(ModelView().getSexByName("Ж"))
//
//                if (filterCheckBoxGroup1.isChecked())
//                    filterByGroup.add(ModelView().getGroupByName("Красные"))
//                if (filterCheckBoxGroup2.isChecked())
//                    filterByGroup.add(ModelView().getGroupByName("Белые"))
//
//                if (filterCheckBoxCity1.isChecked())
//                    filterByCities.add(ModelView().getCityByName("Москва"))
//                if (filterCheckBoxCity2.isChecked())
//                    filterByCities.add(ModelView().getCityByName("Питер"))
//
//                this.currentPersons = ModelView().filterUser(filterByCities, filterByGroup, filterBySex)
//
////                if (sortedOption.text.toString() == "Нет") {
////                    personsList.adapter = PersonsAdapter(this.currentPersons, this)
////                } else if (sortedOption.text.toString() == "A -> Я") {
////                    personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}, this)
////                } else if (sortedOption.text.toString() == "Я -> A")  {
////                    personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}.reversed(), this)
////                }
//
//            }
//        }
//
//        filterCheckBoxSex1.setOnClickListener(checkBoxOnClickListener)
//        filterCheckBoxSex2.setOnClickListener(checkBoxOnClickListener)
//        filterCheckBoxGroup1.setOnClickListener(checkBoxOnClickListener)
//        filterCheckBoxGroup2.setOnClickListener(checkBoxOnClickListener)
//        filterCheckBoxCity1.setOnClickListener(checkBoxOnClickListener)
//        filterCheckBoxCity2.setOnClickListener(checkBoxOnClickListener)
//
//
//
//
//
//
//
//    }
//}
//
//

package com.example.secondapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model_kotlin.ModelView
import model_kotlin.User
import model_kotlin.user_city
import model_kotlin.user_group
import model_kotlin.user_sex
//import model_kotlin.users

class PersonsActivity : AppCompatActivity() {
    var currentPersons: List<User> = emptyList()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        currentPersons = ModelView(this).getAllUsers()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persons)

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

        val filterCheckBoxSex1 = findViewById<CheckBox>(R.id.filterCheckBoxSex1)
        val filterCheckBoxSex2 = findViewById<CheckBox>(R.id.filterCheckBoxSex2)
        val filterCheckBoxGroup1 = findViewById<CheckBox>(R.id.filterCheckBoxGroup1)
        val filterCheckBoxGroup2 = findViewById<CheckBox>(R.id.filterCheckBoxGroup2)
        val filterCheckBoxCity1 = findViewById<CheckBox>(R.id.filterCheckBoxCity1)
        val filterCheckBoxCity2 = findViewById<CheckBox>(R.id.filterCheckBoxCity2)


        val checkBoxOnClickListener = View.OnClickListener { _ ->
            run {
                val filterBySex = mutableListOf<user_sex>()
                val filterByGroup = mutableListOf<user_group>()
                val filterByCities = mutableListOf<user_city>()

                if (filterCheckBoxSex1.isChecked())
                    filterBySex.add(ModelView(this).getSexByName("М"))
                if (filterCheckBoxSex2.isChecked())
                    filterBySex.add(ModelView(this).getSexByName("Ж"))

                if (filterCheckBoxGroup1.isChecked())
                    filterByGroup.add(ModelView(this).getGroupByName("Красные"))
                if (filterCheckBoxGroup2.isChecked())
                    filterByGroup.add(ModelView(this).getGroupByName("Белые"))

                if (filterCheckBoxCity1.isChecked())
                    filterByCities.add(ModelView(this).getCityByName("Москва"))
                if (filterCheckBoxCity2.isChecked())
                    filterByCities.add(ModelView(this).getCityByName("Питер"))

                this.currentPersons = ModelView(this).filterUser(filterByCities, filterByGroup, filterBySex)

                if (sortedOption.text.toString() == "Нет") {
                    personsList.adapter = PersonsAdapter(this.currentPersons, this)
                } else if (sortedOption.text.toString() == "A -> Я") {
                    personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}, this)
                } else if (sortedOption.text.toString() == "Я -> A")  {
                    personsList.adapter = PersonsAdapter(this.currentPersons.sortedBy{U -> U.name}.reversed(), this)
                }

            }
        }

        filterCheckBoxSex1.setOnClickListener(checkBoxOnClickListener)
        filterCheckBoxSex2.setOnClickListener(checkBoxOnClickListener)
        filterCheckBoxGroup1.setOnClickListener(checkBoxOnClickListener)
        filterCheckBoxGroup2.setOnClickListener(checkBoxOnClickListener)
        filterCheckBoxCity1.setOnClickListener(checkBoxOnClickListener)
        filterCheckBoxCity2.setOnClickListener(checkBoxOnClickListener)







    }
}



