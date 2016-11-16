package com.yujie.letwechat.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.yujie.kotlinfulicenter.model.bean.User

/**
 * Created by yujie on 16-10-31.
 */
class DBHelper(
        context: Context?)
        : SQLiteOpenHelper(context,"letwechat.db",null,1){
    val TAG : String = DBHelper::class.java.simpleName

    override fun onCreate(db: SQLiteDatabase) {
        val user_sql = "create table if not exists t_user( " +
                "name char(20) primary key," +
                "uid char(11) not null," +
                "sex char(5)," +
                "b_class char(20)," +
                "b_department char(20)," +
                "b_bedroom Integer," +
                "status Integer,"+
                "user_nick char(20)," +
                "password char(16)," +
                "avatar char(20) " +
                ");"
        db.execSQL(user_sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addUser(user: User, status: Int): Boolean {
        Log.e(TAG,"addUser "+user.toString())
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", user.name)
        values.put("uid", user.uid)
        values.put("sex", user.sex)
        values.put("b_class", user.b_class)
        values.put("b_department", user.b_department)
        values.put("b_bedroom", user.b_bedroom)
        values.put("user_nick", user.user_nick)
        values.put("status", status)
        values.put("password",user.password)
        val insert = db.insert("t_user", null, values)
        Log.e(TAG, "addUser: " + insert)
        return insert > 0
    }

    fun updateUser(user: User, status: Int): Boolean {
        Log.e(TAG,"updateUser "+user.toString())
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", user.name)
        values.put("uid", user.uid)
        values.put("sex", user.sex)
        values.put("b_class", user.b_class)
        values.put("b_department", user.b_department)
        values.put("b_bedroom", user.b_bedroom)
        values.put("user_nick", user.user_nick)
        values.put("status", status)
        values.put("password",user.password)
        val insert = db.update("t_user",values,"user_nick=?", arrayOf(user.user_nick))
        Log.e(TAG, "updateUser: " + insert)
        return insert > 0
    }

    fun updateStatus(status: Int, uid: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("status", status)
        val update = db.update("t_user", values, "user_nick=?", arrayOf(uid))
        Log.e(TAG, "updateStatus: " + update)
        return update > 0
    }

    fun findLoginUser(): User? {
        val db = readableDatabase
        val sql = "select * from t_user where status=1"
        val cursor = db.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val uid = cursor.getString(cursor.getColumnIndex("uid"))
            val sex = cursor.getString(cursor.getColumnIndex("sex"))
            val b_class = cursor.getString(cursor.getColumnIndex("b_class"))
            val b_department = cursor.getString(cursor.getColumnIndex("b_department"))
            val b_bedroom = cursor.getString(cursor.getColumnIndex("b_bedroom"))
            val user_nick = cursor.getString(cursor.getColumnIndex("user_nick"))
            val password = cursor.getString(cursor.getColumnIndex("password"))
            val user = User(name,uid,sex,b_class,b_department,b_bedroom,user_nick,null,password)
            return user
        }
        return null
    }

    fun findUserById(id:String): User? {
        val db = readableDatabase
        val sql = "select * from t_user where uid=?"
        val cursor = db.rawQuery(sql,arrayOf(id))
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val uid = cursor.getString(cursor.getColumnIndex("uid"))
            val sex = cursor.getString(cursor.getColumnIndex("sex"))
            val b_class = cursor.getString(cursor.getColumnIndex("b_class"))
            val b_department = cursor.getString(cursor.getColumnIndex("b_department"))
            val b_bedroom = cursor.getString(cursor.getColumnIndex("b_bedroom"))
            val user_nick = cursor.getString(cursor.getColumnIndex("user_nick"))
            val password = cursor.getString(cursor.getColumnIndex("password"))
            val user = User(name,uid,sex,b_class,b_department,b_bedroom,user_nick,null,password)
            return user
        }
        return null
    }
}