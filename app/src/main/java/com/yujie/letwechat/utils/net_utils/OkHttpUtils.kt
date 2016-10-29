package com.yujie.letwechat.utils.net_utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.File
import java.io.IOException
import java.net.URLConnection
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * Created by yujie on 16-10-28.
 */
class OkHttpUtils<T>(context: Context){
    private var mOkHttpClient : OkHttpClient? = null
    /** Handle message*/
    private var mHandler : Handler? = null
    private var mFileBody : RequestBody? = null
    private var mFormBody : FormBody.Builder? = null
    private var mMultiPartBuild : MultipartBody.Builder? = null
    private var listener : OnCompleteListener<T>? = null
    private var mBuilder : OkHttpClient.Builder? = null
    private var mUrl : StringBuilder? = null
    private var mCls : Class<T>? = null
    private var mCallBack : Callback? = null


    init {
        if (mOkHttpClient == null) {
            synchronized(OkHttpUtils.javaClass){
                if (mOkHttpClient == null) {
                    mBuilder = OkHttpClient.Builder()
                    val cacheDir = context.externalCacheDir
                    mOkHttpClient = mBuilder!!
                                    .connectTimeout(10,TimeUnit.SECONDS)
                                    .writeTimeout(20,TimeUnit.SECONDS)
                                    .readTimeout(10,TimeUnit.SECONDS)
                                    .cache(Cache(cacheDir,10*(1 shl 20)))
                                    .build()
                }
            }
        }
        initHandler()
    }

    private fun initHandler() {
        mHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when(msg.what){
                    RESULT_ERROR    ->  {
                        listener?.onError(msg.obj?.toString())
                    }
                    RESULT_SUCCESS  ->  {
                        val obj : T = msg.obj as T
                        listener?.onSuccess(obj)
                    }
                }
            }
        }
    }

    interface OnCompleteListener<T> {
        fun onSuccess(result: T)
        fun onError(msg : String?)
    }

    /**
     * set connect time out
     */
    fun connectTimeOut(time: Long): OkHttpUtils<T> {
        if (mBuilder == null) {
            return this
        }
        mBuilder?.connectTimeout(time,TimeUnit.SECONDS)
        return this
    }

    /**
     * set write time out
     */
    fun writeTimeOut(time: Long): OkHttpUtils<T> {
        if (mBuilder == null) {
            return this
        }
        mBuilder?.writeTimeout(time,TimeUnit.SECONDS)
        return this
    }

    /**
     * set read time out
     */
    fun readTimeOut(time: Long): OkHttpUtils<T> {
        if (mBuilder == null) {
            return this
        }
        mBuilder?.readTimeout(time,TimeUnit.SECONDS)
        return this
    }

    /**
     * set cache
     */
    fun setCache(file: File, size: Long): OkHttpUtils<T> {
        if (mBuilder == null) {
            return this
        }
        mBuilder?.cache(Cache(file,size))
        return this
    }

    /**
     * add file to request body
     */
    fun addFile(file: File): OkHttpUtils<T> {
        mFileBody = RequestBody.create(null,file)
        return this
    }

    /**
     * add a special type of file
     */
    fun addFile(type:String,file: File): OkHttpUtils<T> {
        mFileBody = RequestBody.create(MediaType.parse(type),file)
        return this
    }

    /**
     * add a guest file
     */
    fun addFile2(file: File): OkHttpUtils<T> {
        if (mUrl == null) {
            return this
        }
        val requestBody = RequestBody.create(MediaType.parse(guessMimeType(file.name)), file)
        mFileBody = MultipartBody.Builder().addFormDataPart("fileName",file.name,requestBody).build()
        return this
    }

    /**
     * method post
     */
    fun post(): OkHttpUtils<T> {
        mFormBody = FormBody.Builder()
        return this
    }

    /**
     * add url
     */
    fun url(url: String): OkHttpUtils<T> {
        mUrl = StringBuilder(url)
        return this
    }

    /**
     * set targetclass ,which the result to cast
     */
    fun targetClass(cls: Class<T>): OkHttpUtils<T> {
        mCls = cls
        return this
    }

    /**
    * add params
     */
    fun addParams(key: String, value: String): OkHttpUtils<T> {
        try {
            if (mFormBody != null) {
                mFormBody?.add(key,URLEncoder.encode(value, UTF8))
            }else{
                if (mUrl?.indexOf("?") == -1){
                    mUrl?.append("?")
                }else{
                    mUrl?.append("&")
                }
                mUrl?.append(key)
                        ?.append("=")
                        ?.append(URLEncoder.encode(value, UTF8))
            }

        }catch (e:UnsupportedOperationException){
            Log.e(TAG,"addParams :-> $e")
        }
        return this
    }

    /**
     * add post params
     */
    fun addFormParams(key: String, value: String): OkHttpUtils<T> {
        if (mMultiPartBuild == null) {
            mMultiPartBuild = MultipartBody.Builder()
            mMultiPartBuild?.setType(MultipartBody.FORM)
            try {
                mUrl?.append("?")
                        ?.append(key)
                        ?.append("=")
                        ?.append(URLEncoder.encode(value, UTF8))
            }catch (e:UnsupportedOperationException){
                Log.e(TAG,"addFormParams: -> $e")
            }
        }else if (mUrl!!.indexOf("?") > -1){
            mMultiPartBuild?.addFormDataPart(key,value)
        }
        return this
    }

    /**
     * add post params
     */
    fun addFormParams(name: String, fileName: String, type: String, file: File): OkHttpUtils<T> {
        if (mMultiPartBuild == null) {
            return this
        }
        mMultiPartBuild?.addFormDataPart(name,fileName,RequestBody.create(MediaType.parse(type), file))
        return this
    }

    /**
     * execute request
     */
    fun execute(listener: OnCompleteListener<T>): Unit {
        if (listener != null) {
            this.listener = listener
        }
        val build = Request.Builder().url(mUrl.toString())
        Log.e(TAG,"execute : -> $mUrl")
        if (mFormBody != null) {
            val body = mFormBody?.build()
            build.post(body)
        }
        if (mFileBody != null) {
            build.post(mFileBody)
        }
        if (mMultiPartBuild != null) {
            val body = mMultiPartBuild?.build()
            build.post(body)
        }
        val request = build.build()
        val call = mOkHttpClient?.newCall(request)
        if (mCallBack != null) {
            call?.enqueue(mCallBack)
            return
        }
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val message = Message.obtain()
                message.what = RESULT_ERROR
                message.obj = e.message
                mHandler?.sendMessage(message)
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body().string()
                when{
                    mCls!!.equals(String::class.java)     -> {
                        val msg = Message.obtain()
                        msg.what = RESULT_SUCCESS
                        msg.obj = json
                        mHandler?.sendMessage(msg)
                    }
                    mCls!!.equals(Bitmap::class.java)     -> {
                        Log.i(TAG, "onResponse: " + response.body())
                        val ins = response.body().byteStream()
                        Log.i(TAG, "onResponse: " + ins)
                        val bitmap = BitmapFactory.decodeStream(ins)
                        Log.i(TAG, "onResponse: " + bitmap)
                        val msg = Message.obtain()
                        msg.what = RESULT_SUCCESS
                        msg.obj = bitmap
                        mHandler?.sendMessage(msg)
                    }
                    else                                ->  {
                        val gson = Gson()
                        val value = gson.fromJson<T>(json, mCls)
                        val msg = Message.obtain()
                        msg.what = RESULT_SUCCESS
                        msg.obj = value
                        mHandler?.sendMessage(msg)
                    }
                }
            }
        })
    }

    /**
     * do in backgroud
     */
    fun doInBackGround(callback: Callback): OkHttpUtils<T> {
        mCallBack = callback
        return this
    }

    /**
     * use to set a special listener
     */
    fun onPostExecute(listener: OnCompleteListener<T>): OkHttpUtils<T> {
        this.listener = listener
        return this
    }

    /**
     * set a runnable
     */
    fun onPreExecute(r: Runnable): OkHttpUtils<T> {
        r.run()
        return this
    }

    /**
     * send own message
     */
    fun sendMessage(msg: Message): Unit {
        mHandler?.sendMessage(msg)
    }

    /**
    * parse Json to object
     */
    fun parseJson(json: String, clz: Class<T>): T {
        val gson = Gson()
        val result : T = gson.fromJson(json, clz)
        return result

    }

    /**
     * use to download message
     */
    fun downloadImage(imgUrl: String): Bitmap? {
        try {
            val client = OkHttpClient()
            val request = Request.Builder().url(imgUrl).build()
            val response = mOkHttpClient?.newCall(request)?.execute()
            val ins = response?.body()?.byteStream()
            Log.e(TAG, "setInfo: " + ins + "\n" + response + "\n" + imgUrl)
            val bm = BitmapFactory.decodeStream(ins)
            return bm
        }catch (e:Exception){
            Log.e(TAG,"downloadImage :-> $e")
        }
        return null
    }

    /**
     * guess file type
     */
    private fun guessMimeType(name: String?): String? {
        val map = URLConnection.getFileNameMap()
        var path = map.getContentTypeFor(name)
        if (path == null) {
            path = "application/octet-stream"
        }
        return path
    }

    /**
     * message code
     */
    companion object{
        val TAG : String = OkHttpUtils::class.java.simpleName
        val UTF8 = "utf-8"
        val RESULT_SUCCESS = 0
        val RESULT_ERROR = 1
        val DOWNLOAD_START = 2
        val DOWNLOADING = 3
        val DOWNLOAD_FINISH = 4
    }
}