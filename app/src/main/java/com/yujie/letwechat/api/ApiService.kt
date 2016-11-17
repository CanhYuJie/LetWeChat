package com.yujie.letwechat.api

import com.yujie.kotlinfulicenter.model.bean.*
import com.yujie.letwechat.I
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap
import rx.Observable
import java.util.*

/**
 * Created by yujie on 16-11-9.
 */
interface ApiService {
    @GET(I.REQUEST_SERVERSTATUS)
    fun getNetWorkState(): Observable<Result>


    @POST(I.REQUEST_LOGIN)
    fun login(@Query(I.User.USER_NAME) name:String,
              @Query(I.User.MPASSWORD) password:String): Observable<ResultData>


    @POST(I.REQUEST_REGISTER)
    fun register(@Query(I.User.NAME) name:String,
                 @Query(I.User.UID) uid: String,
                 @Query(I.User.NICK) nick: String,
                 @Query(I.User.PASSWORD) password:String): Observable<Result>


    @GET(I.REQUEST_UNREGISTER)
    fun unregister(@Query(I.User.NICK) nick: String): Observable<Result>
    @GET(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
    fun getContacts(@Query(I.Contact.M_UID) m_uid: String): Observable<ArrayList<User>>
    @GET(I.MANAGER_SERVER)
    fun getProfile(@QueryMap par:Map<String,String>): Observable<Result>
    @GET(I.MANAGER_SERVER)
    fun getAllClass(@Query(I.REQUEST_KEY) request: String,
                    @Query(I.OPT_USER) user:String): Observable<ArrayList<ClassObj>>
    @GET(I.MANAGER_SERVER)
    fun getAllBedroom(@Query(I.REQUEST_KEY) request: String,
                      @Query(I.OPT_USER) user:String):Observable<ArrayList<BedRoom>>
    @GET(I.MANAGER_SERVER)
    fun getAllDepartment(@Query(I.REQUEST_KEY) request: String,
                         @Query(I.OPT_USER) user:String):Observable<ArrayList<Department>>
    @POST(I.MANAGER_SERVER)
    fun updateUser(@QueryMap par: Map<String, String>):Observable<Result>
    @GET(I.REQUEST_GET_USER_BY_NICK)
    fun getUserByNick(@Query(I.Student.USER_NICK) user_nick:String):Observable<ResultData>
    @GET(I.REQUEST_ADD_CONTACT)
    fun addContact(@Query(I.Contact.M_UID) m_uid: String,
                   @Query(I.Contact.O_UID) o_uid: String):Observable<Result>
    @GET(I.REQUEST_GET_USERS_BY_NICK)
    fun getUsersByNick(@Query(I.Student.USER_NICK) user_nick: String): Observable<ArrayList<User>>
}