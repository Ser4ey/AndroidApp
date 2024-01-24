package model_kotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import java.sql.SQLException

class ModelView(context: Context) {
    private val TABLE_USERS_VIEW = "table_users_view"
    private val TABLE_SEX = "table_sex"
    private val TABLE_GROUP = "table_group"
    private val TABLE_CITY = "table_city"

    private val KEY_ID = "id"
    private val KEY_FULL_NAME = "full_name"
    private val KEY_SEX_ID = "sex_id"
    private val KEY_GROUP_ID = "group_id"
    private val KEY_CITY_ID = "city_id"
    private val KEY_VALUE = "value"

    private val dbHelper: DbHelper = DbHelper(context)


    @SuppressLint("Range")
    fun getAllUsers(): List<User> {
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
        val filteredUsers = mutableListOf<User>()

        val query = "SELECT * FROM $TABLE_USERS_VIEW " +
                "WHERE $KEY_CITY_ID IN (${byCity.joinToString { it.id.toString() }}) " +
                "AND $KEY_GROUP_ID IN (${byGroup.joinToString { it.id.toString() }}) " +
                "AND $KEY_SEX_ID IN (${bySex.joinToString { it.id.toString() }})"

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
                filteredUsers.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return filteredUsers
    }

    fun delUser(userId: Int): Boolean {
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
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(KEY_FULL_NAME, user.name)
        values.put(KEY_SEX_ID, user.sex.id)
        values.put(KEY_GROUP_ID, user.group.id)
        values.put(KEY_CITY_ID, user.city.id)

        db.insert(TABLE_USERS_VIEW, null, values)
        db.close()
    }

    fun updateUser(newUserData: User): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(KEY_FULL_NAME, newUserData.name)
        values.put(KEY_SEX_ID, newUserData.sex.id)
        values.put(KEY_GROUP_ID, newUserData.group.id)
        values.put(KEY_CITY_ID, newUserData.city.id)

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
        val groups = mutableListOf<user_group>()

        val query = "SELECT * FROM $TABLE_GROUP"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val groupId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val groupName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
                val group = user_group(groupId, groupName)
                groups.add(group)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return groups
    }

    @SuppressLint("Range")
    fun getGroupByName(name: String): user_group {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_GROUP WHERE $KEY_VALUE = ?"
        val cursor = db.rawQuery(query, arrayOf(name))

        var group = user_group(0, "") // Значения по умолчанию, если не найдено
        if (cursor.moveToFirst()) {
            val groupId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val groupName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
            group = user_group(groupId, groupName)
        }

        cursor.close()
        db.close()

        return group
    }

    @SuppressLint("Range")
    fun getAllCities(): List<user_city> {
        val cities = mutableListOf<user_city>()

        val query = "SELECT * FROM $TABLE_CITY"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val cityId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val cityName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
                val city = user_city(cityId, cityName)
                cities.add(city)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return cities
    }

    @SuppressLint("Range")
    fun getCityByName(name: String): user_city {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_CITY WHERE $KEY_VALUE = ?"
        val cursor = db.rawQuery(query, arrayOf(name))

        var city = user_city(0, "") // Значения по умолчанию, если не найдено
        if (cursor.moveToFirst()) {
            val cityId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val cityName = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
            city = user_city(cityId, cityName)
        }

        cursor.close()
        db.close()

        return city
    }

    @SuppressLint("Range")
    fun getAllSex(): List<user_sex> {
        val sexes = mutableListOf<user_sex>()

        val query = "SELECT * FROM $TABLE_SEX"
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val sexId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val sexValue = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
                val sex = user_sex(sexId, sexValue)
                sexes.add(sex)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return sexes
    }

    @SuppressLint("Range")
    fun getSexByName(name: String): user_sex {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_SEX WHERE $KEY_VALUE = ?"
        val cursor = db.rawQuery(query, arrayOf(name))

        var sex = user_sex(0, "") // Значения по умолчанию, если не найдено
        if (cursor.moveToFirst()) {
            val sexId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val sexValue = cursor.getString(cursor.getColumnIndex(KEY_VALUE))
            sex = user_sex(sexId, sexValue)
        }

        cursor.close()
        db.close()

        return sex
    }
}