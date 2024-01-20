package model_kotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context):
    SQLiteOpenHelper(context, "app", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val query1 = "CREATE TABLE IF NOT EXISTS table_users_view(" +
                "id INT PRIMARY KEY," +
                "full_name TEXT," +
                "sex_id user_sex," +
                "group_id user_group," +
                "city_id user_city," +
                "FOREIGN KEY(sex_id) REFERENCES table_sex(sex_id)," +
                "FOREIGN KEY(group_id) REFERENCES table_group(group_id)," +
                "FOREIGN KEY(city_id) REFERENCES table_city(city_id))"
        db.execSQL(query1)

        val query2 = "CREATE TABLE IF NOT EXISTS table_sex(" +
                "sex_id INT PRIMARY KEY," +
                "value TEXT)"
        db.execSQL(query2)

        val query21 = "INSERT INTO table_sex (sex_id, value) VALUES (0, 'М')"
        val query22 = "INSERT INTO table_sex (sex_id, value) VALUES (1, 'Ж')"
        db.execSQL(query21)
        db.execSQL(query22)

        val query3 = "CREATE TABLE IF NOT EXISTS table_group(" +
                "group_id INT PRIMARY KEY," +
                "value TEXT)"
        db.execSQL(query3)

        val query31 = "INSERT INTO table_group (group_id, value) VALUES (0, 'Красные')"
        val query32 = "INSERT INTO table_group (group_id, value) VALUES (1, 'Белые')"
        db.execSQL(query31)
        db.execSQL(query32)

        val query4 = "CREATE TABLE IF NOT EXISTS table_city(" +
                "city_id INT PRIMARY KEY," +
                "value TEXT)"
        db.execSQL(query4)

        val query41 = "INSERT INTO table_city (city_id, value) VALUES (0, 'Москва')"
        val query42 = "INSERT INTO table_city (city_id, value) VALUES (1, 'Питер')"
        db.execSQL(query41)
        db.execSQL(query42)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS table_users_view;")
        db.execSQL("DROP TABLE IF EXISTS table_sex;")
        db.execSQL("DROP TABLE IF EXISTS table_group;")
        db.execSQL("DROP TABLE IF EXISTS table_city;")
        onCreate(db)
    }
}