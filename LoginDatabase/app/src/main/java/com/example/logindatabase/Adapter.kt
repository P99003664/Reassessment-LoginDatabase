package com.example.logindatabase

import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.service.autofill.UserData
import java.util.*
import kotlin.collections.ArrayList

class Adapter(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // create table sql query
    private val dbtable="UserInfo"
    private val dbname="users"
    private val username="Username"
    private val password="Password"
    private val email="Email"
    private val mobile="Mobile Number"
    private val CREATE_SQL_TABLE = ("CREATE TABLE " + dbtable + "("
            + username + " TEXT," + password + " TEXT,"
            + email + " TEXT," + mobile + " INTEGER" + ")")
    private val DROP_TABLE_NAME = "DROP TABLE IF EXISTS $dbtable"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_SQL_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_NAME)
        onCreate(db)

    }

    fun getAllUser(): ArrayList<Any> {
        val columns = arrayOf(username,password,email,mobile)
        val sortOrder = "$username ASC"
       val userList = ArrayList<Any>()


        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(dbtable, columns, null, null, null, null, sortOrder)
        if (cursor.moveToFirst()) {
            do {
                val user = Userdata(username = cursor.getString(cursor.getColumnIndex(username)),
                    password = cursor.getString(cursor.getColumnIndex(password)),
                    email = cursor.getString(cursor.getColumnIndex(email)),
                    mobile = cursor.getString(cursor.getColumnIndex(mobile)).toInt())

                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user: Userdata) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(username, user.username)
        values.put(password, user.password)
        values.put(email, user.email)
        values.put(mobile,user.mobile)

        // Inserting Row
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun updateUser(user: Userdata) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(username, user.username)
        values.put(password, user.password)
        values.put(email, user.email)
        values.put(mobile,user.mobile)
        db.update(TABLE_NAME, values, "$mobile = ?",
            arrayOf(user.mobile.toString()))
        db.close()
    }
    fun deleteUser(user: Userdata) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_NAME, "$mobile = ?",
            arrayOf(user.mobile.toString()))
        db.close()


    }
    fun checkUser(username: String): Boolean {

        val columns = arrayOf(username)
        val db = this.readableDatabase
        val selection = "$username= ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(dbtable, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    fun checkUser(username: String, password: String): Boolean {
        val columns = arrayOf(username)
        val db = this.readableDatabase
        val selection = "$username = ? AND $password= ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(dbtable, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "users.db"

        // User table name
        private val TABLE_NAME = "userInfo"
        private val username = "username"
        private val password = "password"
        private val email = "email"
        private val mobile= "mobile"
    }
}