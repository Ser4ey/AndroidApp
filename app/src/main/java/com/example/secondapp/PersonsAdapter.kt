package com.example.secondapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import model_kotlin.User

class PersonsAdapter(var items: List<User>, var context: Context) : RecyclerView.Adapter<PersonsAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_list_name)
        val sex: TextView = view.findViewById(R.id.item_list_sex)
        val group: TextView = view.findViewById(R.id.item_list_group)
        val city: TextView = view.findViewById(R.id.item_list_city)
        val updateUserBtn: Button = view.findViewById(R.id.item_list_updateUserBtn)
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


        holder.updateUserBtn.setOnClickListener {
            val intent = Intent(context, UpdatePersonActivity::class.java)

            intent.putExtra("userId", items[position].id)
            intent.putExtra("userName", items[position].name)
            intent.putExtra("userSex", items[position].sex.value)
            intent.putExtra("userSexId", items[position].sex.id)
            intent.putExtra("userGroup", items[position].group.name)
            intent.putExtra("userGroupId", items[position].group.id)
            intent.putExtra("userCity", items[position].city.name)
            intent.putExtra("userCityId", items[position].city.id)
            context.startActivity(intent)
        }
    }

}