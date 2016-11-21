package com.yujie.letwechat.utils.common_utils

import android.Manifest
import android.app.Activity
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.yujie.letwechat.R
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern



/**
 * Created by yujie on 16-10-28.
 */

/**
 * Toast utils
 */
fun showLongToast(context: Context,msg: String){
    Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
}

fun showShortToast(context: Context,msg: String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}

fun showLongToastRes(context: Context,msg: Int){
    val str = context.resources.getString(msg)
    Toast.makeText(context,str,Toast.LENGTH_LONG).show()
}

fun showShortToastRes(context: Context,msg: Int){
    val str = context.resources.getString(msg)
    Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
}

/**
 * finish activity
 */
fun Kfinish(activity:Activity){
    activity.finish()
    activity.overridePendingTransition(R.anim.push_right_in,
            R.anim.push_right_out)
}

/**
 * start activity with a bean object
 */
fun <T>KstartActivity(activity: Activity,cls: Class<T>,
                  tag: String,value: Serializable){
    val intent = Intent()
    intent.setClass(activity,cls)
    if (value != null) {
        val bundle = Bundle()
        bundle.putSerializable(tag,value)
        intent.putExtras(bundle)
    }
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}

/**
 * start Activity with a string value
 */
fun <T>KstartActivity(activity : Activity, cls : Class<T>,
                  tag : String, value : String) {
    val intent = Intent()
    intent.setClass(activity,cls)
    if (tag != null && value != null) {
        intent.putExtra(tag,value)
    }
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}

/**
 * start activity with a integer value
 */
fun <T>KstartActivity(activity : Activity, cls : Class<T>,
                  tag : String, value : Int) {
    val intent = Intent()
    intent.setClass(activity,cls)
    if (tag != null && value != null) {
        intent.putExtra(tag,value)
    }
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}
fun <T>KstartActivity(activity : Activity, cls : Class<T>) {
    val intent = Intent()
    intent.setClass(activity,cls)
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}

fun <T>KstartActivity(activity : FragmentActivity, cls : Class<T>) {
    val intent = Intent()
    intent.setClass(activity,cls)
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}

fun <T>SingleStartActivity(activity : FragmentActivity, cls : Class<T>) {
    val intent = Intent()
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    intent.setClass(activity,cls)
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}

/**
 * start activity with a boolean value
 */
fun <T>KstartActivity(activity : Activity, cls : Class<T>,
                  tag : String, value : Boolean) {
    val intent = Intent()
    intent.setClass(activity,cls)
    if (tag != null && value != null) {
        intent.putExtra(tag,value)
    }
    activity.startActivity(intent)
    activity.overridePendingTransition(R.anim.push_left_in,
            R.anim.push_left_out)
}

/**
 * invalid text is Empty
 */
fun invalidEmpty(view: EditText): Boolean {
    val text = view.text.toString()
    if (text.isEmpty()) {
        view.error = "no input,please input"
        view.requestFocus()
        return true
    }
    return false
}

/**
 * get Internet connect state
 */
fun getNetwokState(context: Context): Boolean {
    if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET)
    != PackageManager.PERMISSION_GRANTED){
        return false
    }else{
        val service : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (service == null) {
            Log.e("GetNetworkState","couldn't get connectivity manager")
        }else{
            val networkInfo = service.allNetworkInfo
            if (networkInfo != null) {
                for (i in 0..networkInfo.size-1){
                    if (networkInfo[i].isAvailable){
                        Log.e("GetNetwokState","Network is available")
                        return true
                    }
                }
            }
        }
    }
    Log.e("GetNetwokState","Network is not available ")
    return false
}

/**
 * notify a msg
 */
fun notifyStringMessage(context : Context, msg : String,title : String, content : String,
                        intent : Intent){
    val notifyService : NotificationManager = context.
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
    val notification = Notification.Builder(context)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(msg)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setNumber(1)
            .build()
    notification.flags = Notification.FLAG_AUTO_CANCEL
    notifyService.notify(0,notification)
}

/**
 * remove a value from sharedPreferences
 */
fun removeValue(context: Context,key : String){
    val edit = getSharedPreferences(context).edit()
    edit.remove(key)
    val result = edit.commit()
    if (result){
        Log.e("RemoveValue","$result is removed")
    }
}

fun getSharedPreferences(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}

/**
 * get a String value in SharedPreferences
 */
fun getStringValueInSp(context: Context, key: String): String {
    return getSharedPreferences(context).getString(key,"")
}

/**
 * get a Integer value in SharedPreferences
 */
fun getIntValueInSP(context: Context, key: String): Int{
    return getSharedPreferences(context).getInt(key,0)
}

/**
 * get a Boolean value in SharedPreferences
 */
fun getBooleanValueInSp(context: Context,key: String): Boolean{
    return getSharedPreferences(context).getBoolean(key,false)
}

/**
 * put a String value to SharedPreferences
 */
fun putStringToSp(context : Context, key : String,value : String) : Boolean {
    val edit = getSharedPreferences(context).edit()
    edit.putString(key,value)
    return edit.commit()
}

/**
 * put a Integer value to SharedPreferences
 */
fun putStringToSp(context : Context, key : String,value : Int) : Boolean {
    val edit = getSharedPreferences(context).edit()
    edit.putInt(key,value)
    return edit.commit()
}

/**
 * put a boolean value to SharedPreferences
 */
fun putStringToSp(context : Context, key : String,value : Boolean) : Boolean {
    val edit = getSharedPreferences(context).edit()
    edit.putBoolean(key,value)
    return edit.commit()
}

/**
 * get the result if sharedPreferences has the value
 */
fun hasVlaueInSp(context: Context, key: String): Boolean {
    return getSharedPreferences(context).contains(key)
}

/**
 * format a String value and parse it to Date
 */
fun stringToDate(str: String): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd hh:mm")
    var date : Date? = null
    try {
        date = format.parse(str)
    }catch (e:ParseException){

        Log.e("StringToDate","Exception:-> $e")
    }
    return date
}

/**
 * return the true if the string value is a email
 */
fun isEmail(email: String): Boolean {
    val format = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
    val pattern = Pattern.compile(format)
    val matcher = pattern.matcher(email)
    return matcher.matches()
}

/**
 * return true if the value be a phone number
 */
fun isPhone(phone: String): Boolean {
    val format = "^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$"
    val pattern = Pattern.compile(format)
    val matcher = pattern.matcher(phone)
    return matcher.matches()
}

/**
 * return true if the value be a number
 */
fun isNumber(number: String): Boolean {
    val compile = Pattern.compile("[0-9]*")
    val matcher = compile.matcher(number)
    return matcher.matches()
}

/**
 * return version name
 */
fun getVersionNumber(context: Context): String {
    try {
        val manager = context.packageManager
        val info = manager.getPackageInfo(context.packageName, 0)
        return info.versionName
    }catch (e:Exception){
        Log.e("GetVersionNumber","GetVersionNumber:->$e ")
        return ""
    }
}

