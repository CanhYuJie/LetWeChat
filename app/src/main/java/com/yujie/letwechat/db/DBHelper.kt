package com.yujie.letwechat.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.yujie.kotlinfulicenter.model.bean.RetDataBean

/**
 * Created by yujie on 16-10-31.
 */
class DBHelper(
        context: Context?)
        : SQLiteOpenHelper(context,"superwechat.db",null,1){
    val TAG : String = DBHelper::class.java.simpleName

    override fun onCreate(db: SQLiteDatabase) {
        val user_sql = "create table if not exists t_user( " +
                "muserName char(20) primary key," +
                "muserNick char(20) not null," +
                "mavatarId Integer not null," +
                "mavatarPath char(20)," +
                "mavatarSuffix char(20)," +
                "mavatarType Integer," +
                "status Integer,"+
                "mavatarLastUpdateTime char(20)" +
                ");"
        db.execSQL(user_sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addUser(user: RetDataBean, status: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("muserName", user.muserName)
        values.put("muserNick", user.muserNick)
        values.put("mavatarId", user.mavatarId)
        values.put("mavatarPath", user.mavatarPath)
        values.put("mavatarSuffix", user.mavatarSuffix)
        values.put("mavatarType", user.mavatarType)
        values.put("mavatarLastUpdateTime", user.mavatarLastUpdateTime)
        values.put("status", status)
        val insert = db.insert("t_user", null, values)
        Log.e(TAG, "addUser: " + insert)
        return insert > 0
    }

    fun updateStatus(status: Int, uid: String): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("status", status)
        val update = db.update("t_user", values, "muserName=?", arrayOf(uid))
        Log.e(TAG, "updateStatus: " + update)
        return update > 0
    }

    fun findLoginUser(): RetDataBean? {
        val db = readableDatabase
        val sql = "select * from t_user where status=1"
        val cursor = db.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val muserName = cursor.getString(cursor.getColumnIndex("muserName"))
            val muserNick = cursor.getString(cursor.getColumnIndex("muserNick"))
            val mavatarId = cursor.getInt(cursor.getColumnIndex("mavatarId"))
            val mavatarPath = cursor.getString(cursor.getColumnIndex("mavatarPath"))
            val mavatarSuffix = cursor.getString(cursor.getColumnIndex("mavatarSuffix"))
            val mavatarType = cursor.getInt(cursor.getColumnIndex("mavatarType"))
            val mavatarLastUpdateTime = cursor.getString(cursor.getColumnIndex("mavatarLastUpdateTime"))
            val user = RetDataBean(muserName,muserNick,mavatarId,mavatarPath,mavatarSuffix,mavatarType,mavatarLastUpdateTime)
            return user
        }
        return null
    }

    fun findUserById(id:String): RetDataBean? {
        val db = readableDatabase
        val sql = "select * from t_user where muserName=?"
        val cursor = db.rawQuery(sql,arrayOf(id))
        while (cursor.moveToNext()) {
            val muserName = cursor.getString(cursor.getColumnIndex("muserName"))
            val muserNick = cursor.getString(cursor.getColumnIndex("muserNick"))
            val mavatarId = cursor.getInt(cursor.getColumnIndex("mavatarId"))
            val mavatarPath = cursor.getString(cursor.getColumnIndex("mavatarPath"))
            val mavatarSuffix = cursor.getString(cursor.getColumnIndex("mavatarSuffix"))
            val mavatarType = cursor.getInt(cursor.getColumnIndex("mavatarType"))
            val mavatarLastUpdateTime = cursor.getString(cursor.getColumnIndex("mavatarLastUpdateTime"))
            val user = RetDataBean(muserName,muserNick,mavatarId,mavatarPath,mavatarSuffix,mavatarType,mavatarLastUpdateTime)
            return user
        }
        return null
    }
}