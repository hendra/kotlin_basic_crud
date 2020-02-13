package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // looping through all rows and adding to list
    // adding to Students list
    val allStudentsList: ArrayList<String>
        get() {
            val studentsArrayList = ArrayList<String>()
            var name = ""
            val selectQuery = "SELECT  * FROM $TABLE_STUDENTS"
            val db = this.readableDatabase
            val c = db.rawQuery(selectQuery, null)
            if (c.moveToFirst()) {
                do {
                    name = c.getString(c.getColumnIndex(KEY_FIRSTNAME))
                    studentsArrayList.add(name)
                } while (c.moveToNext())
                Log.d("array", studentsArrayList.toString())
            }
            return studentsArrayList
        }

    init {
        Log.d("table", CREATE_TABLE_STUDENTS)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_STUDENTS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_STUDENTS'")
        onCreate(db)
    }

    fun addStudentDetail(student: String): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_FIRSTNAME, student)
        // insert row in students table

        return db.insert(TABLE_STUDENTS, null, values)
    }

    companion object {

        var DATABASE_NAME = "student_database"
        private val DATABASE_VERSION = 1
        private val TABLE_STUDENTS = "students"
        private val KEY_ID = "id"
        private val KEY_FIRSTNAME = "name"

        /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
        private val CREATE_TABLE_STUDENTS = ("CREATE TABLE "
                + TABLE_STUDENTS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT );")
    }

}