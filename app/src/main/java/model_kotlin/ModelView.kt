package model_kotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

val user1 = User(1, "Райан Гослинг", user_sex(1, "М"), user_group(2, "Белые"), user_city(1, "Москва"))
val user2 = User(2, "Аня", user_sex(2, "Ж"), user_group(1, "Красные"), user_city(2, "Питер"))
val user3 = User(3, "Ива", user_sex(2, "Ж"), user_group(1, "Красные"), user_city(1, "Москва"))
val users = mutableListOf<User>(user1, user2, user3)
var current_id = 4

class ModelView(context: Context) {
    private val TABLE_USERS_VIEW = "table_users_view"
    private val TABLE_SEX = "table_sex"
    private val TABLE_GROUP = "table_group"
    private val TABLE_CITY = "table_city"

    private val dbHelper: DbHelper = DbHelper(context)

    private val KEY_ID = "id"
    private val KEY_FULL_NAME = "full_name"
    private val KEY_SEX_ID = "sex_id"
    private val KEY_GROUP_ID = "group_id"
    private val KEY_CITY_ID = "city_id"
    private val KEY_VALUE = "value"

    @SuppressLint("Range")
    fun getAllUsers(): List<User> {
//            актуальный список всех пользователей
//            val user1 = User(1, "Oka1", user_sex(1, "М"), user_group(1, "White"), user_city(1, "Москва"))
//            val user2 = User(2, "Pols2", user_sex(1, "Ж"), user_group(1, "Red"), user_city(1, "Питер"))
//            return users

        val users = mutableListOf<User>()
        val query = "SELECT * FROM $TABLE_USERS_VIEW"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val userId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val fullName = cursor.getString(cursor.getColumnIndex(KEY_FULL_NAME))
                val sexId = cursor.getInt(cursor.getColumnIndex(KEY_SEX_ID))
                val groupId = cursor.getInt(cursor.getColumnIndex(KEY_GROUP_ID))
                val cityId = cursor.getInt(cursor.getColumnIndex(KEY_CITY_ID))

                val sex = user_sex(sexId, getValueById(TABLE_SEX, sexId))
                val group = user_group(groupId, getValueById(TABLE_GROUP, groupId))
                val city = user_city(cityId, getValueById(TABLE_CITY, cityId))

                val user = User(userId, fullName, sex, group, city)
                users.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return users
    }

    @SuppressLint("Range")
    private fun getValueById(tableName: String, itemId: Int): String {
        val db = dbHelper.readableDatabase
        val query = "SELECT $KEY_VALUE FROM $tableName WHERE $KEY_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(itemId.toString()))

        var value = ""
        if (cursor.moveToFirst()) {
            value = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
        }

        cursor.close()
        db.close()

        return value
    }


    @SuppressLint("Range")
    fun filterUser(byCity: List<user_city>, byGroup: List<user_group>, bySex: List<user_sex>): List<User>  {
    //        актуальный список всех пользователей, котрые удовлетворяют всем 3 фильтрам
    val filteredUsers = mutableListOf<User>()

    for (user in users){
        if (user.city in byCity && user.group in byGroup && user.sex in bySex)
            filteredUsers.add(user)
    }
    return filteredUsers

//        val filteredUsers = mutableListOf<User>()
//
//        val query = "SELECT * FROM $TABLE_USERS_VIEW " +
//                "WHERE $KEY_CITY_ID IN (${byCity.joinToString { it.id.toString() }}) " +
//                "AND $KEY_GROUP_ID IN (${byGroup.joinToString { it.id.toString() }}) " +
//                "AND $KEY_SEX_ID IN (${bySex.joinToString { it.id.toString() }})"
//
//        val db = dbHelper.readableDatabase
//        val cursor = db.rawQuery(query, null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val userId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//                val fullName = cursor.getString(cursor.getColumnIndex(KEY_FULL_NAME))
//                val sexId = cursor.getInt(cursor.getColumnIndex(KEY_SEX_ID))
//                val groupId = cursor.getInt(cursor.getColumnIndex(KEY_GROUP_ID))
//                val cityId = cursor.getInt(cursor.getColumnIndex(KEY_CITY_ID))
//
//                val sex = user_sex(sexId, getValueById(TABLE_SEX, sexId))
//                val group = user_group(groupId, getValueById(TABLE_GROUP, groupId))
//                val city = user_city(cityId, getValueById(TABLE_CITY, cityId))
//
//                val user = User(userId, fullName, sex, group, city)
//                filteredUsers.add(user)
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//
//        return filteredUsers
    }

    fun delUser(userId: Int): Boolean {
//  удаляет пользователя из бд по id
//        for (user in users) {
//            if (user.id == userId) {
//                return users.remove(user)
//            }
//        }
//        return false


        val db = dbHelper.writableDatabase
        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(userId.toString())

        try {
            val result = db.delete(TABLE_USERS_VIEW, whereClause, whereArgs)
            db.close()
            return result > 0
        } catch (e: SQLException) {
            // Обработка ошибки, например, логирование
            e.printStackTrace()
            db.close()
            return false
        }
    }

    fun addUser(user: User) {
//        user.id - по умолчанию 0, должен задаваться автоматически в бд
//        val new_user = User(current_id, user.name, user.sex,  user.group, user.city)
//        current_id += 1
//        users.add(new_user)

        val db = dbHelper.writableDatabase
        val values = ContentValues()

//        values.put(KEY_ID, user.id)

        values.put(KEY_FULL_NAME, user.name)
        values.put(KEY_SEX_ID, user.sex.id-1)
        values.put(KEY_GROUP_ID, user.group.id-1)
        values.put(KEY_CITY_ID, user.city.id-1)

        db.insert(TABLE_USERS_VIEW, null, values)
        db.close()
    }

    fun updateUser(newUserData: User): Boolean {
//        for (i in users.indices) {
//            if (users[i].id == newUserData.id) {
//                users[i].name = newUserData.name
//                users[i].sex = newUserData.sex
//                users[i].group = newUserData.group
//                users[i].city = newUserData.city
//                return true
//            }
//        }
//        return false

        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(KEY_FULL_NAME, newUserData.name)
        values.put(KEY_SEX_ID, newUserData.sex.id - 1)
        values.put(KEY_GROUP_ID, newUserData.group.id - 1)
        values.put(KEY_CITY_ID, newUserData.city.id - 1)

        val whereClause = "$KEY_ID = ?"
        val whereArgs = arrayOf(newUserData.id.toString())

        try {
            val result = db.update(TABLE_USERS_VIEW, values, whereClause, whereArgs)
            db.close()
            return result > 0
        } catch (e: Exception) {
            // Логирование ошибки
            e.printStackTrace()
            db.close()
            return false
        }
    }

    @SuppressLint("Range")
    fun getAllGroups(): List<user_group> {
        return listOf(user_group(1, "Красные"),  user_group(2, "Белые"))

//        val groups = mutableListOf<user_group>()
//
//        val query = "SELECT * FROM $TABLE_GROUP"
//        val db = dbHelper.readableDatabase
//        val cursor = db.rawQuery(query, null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val groupId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//                val groupName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
//                val group = user_group(groupId, groupName)
//                groups.add(group)
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//
//        return groups
    }

    @SuppressLint("Range")
    fun getGroupByName(name: String): user_group {
        if (name == "Красные") return user_group(1, "Красные")
        return user_group(2, "Белые")

//        val db = dbHelper.readableDatabase
//        val query = "SELECT * FROM $TABLE_GROUP WHERE $KEY_VALUE = ?"
//        val cursor = db.rawQuery(query, arrayOf(name))
//
//        var group = user_group(0, "") // Значения по умолчанию, если не найдено
//        if (cursor.moveToFirst()) {
//            val groupId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//            val groupName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
//            group = user_group(groupId, groupName)
//        }
//
//        cursor.close()
//        db.close()
//
//        return group
    }

    @SuppressLint("Range")
    fun getAllCities(): List<user_city> {
        return listOf(user_city(1, "Москва"),  user_city(2, "Питер"))

//        val cities = mutableListOf<user_city>()
//
//        val query = "SELECT * FROM $TABLE_CITY"
//        val db = dbHelper.readableDatabase
//        val cursor = db.rawQuery(query, null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val cityId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//                val cityName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
//                val city = user_city(cityId, cityName)
//                cities.add(city)
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//
//        return cities
    }

    @SuppressLint("Range")
    fun getCityByName(name: String): user_city {
        if (name == "Москва") return user_city(1, "Москва")
        return user_city(2, "Питер")

//        val db = dbHelper.readableDatabase
//        val query = "SELECT * FROM $TABLE_CITY WHERE $KEY_VALUE = ?"
//        val cursor = db.rawQuery(query, arrayOf(name))
//
//        var city = user_city(0, "") // Значения по умолчанию, если не найдено
//        if (cursor.moveToFirst()) {
//            val cityId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//            val cityName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
//            city = user_city(cityId, cityName)
//        }
//
//        cursor.close()
//        db.close()
//
//        return city
    }

    @SuppressLint("Range")
    fun getAllSex(): List<user_sex> {
        return listOf(user_sex(1, "М"),  user_sex(2, "Ж"))

//        val sexes = mutableListOf<user_sex>()
//
//        val query = "SELECT * FROM $TABLE_SEX"
//        val db = dbHelper.readableDatabase
//        val cursor = db.rawQuery(query, null)
//
//        if (cursor.moveToFirst()) {
//            do {
//                val sexId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//                val sexValue = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
//                val sex = user_sex(sexId, sexValue)
//                sexes.add(sex)
//            } while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//
//        return sexes
    }

    @SuppressLint("Range")
    fun getSexByName(name: String): user_sex {
        if (name == "М") return user_sex(1, "М")
        return user_sex(2, "Ж")

//        val db = dbHelper.readableDatabase
//        val query = "SELECT * FROM $TABLE_SEX WHERE $KEY_VALUE = ?"
//        val cursor = db.rawQuery(query, arrayOf(name))
//
//        var sex = user_sex(0, "") // Значения по умолчанию, если не найдено
//        if (cursor.moveToFirst()) {
//            val sexId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
//            val sexValue = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
//            sex = user_sex(sexId, sexValue)
//        }
//
//        cursor.close()
//        db.close()
//
//        return sex
    }
}