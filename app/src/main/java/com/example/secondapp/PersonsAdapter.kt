package com.example.secondapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model_kotlin.User

class PersonsAdapter(var items: List<User>, var context: Context) : RecyclerView.Adapter<PersonsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_list_name)
        val sex: TextView = view.findViewById(R.id.item_list_sex)
        val group: TextView = view.findViewById(R.id.item_list_group)
        val city: TextView = view.findViewById(R.id.item_list_city)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.sex.text = "Пол: ${items[position].sex.value}"
        holder.group.text = "Группа: ${items[position].group.name}"
        holder.city.text = "Город: ${items[position].city.name}"
    }

}