package com.yujie.letwechat

interface I {

    interface User {
        companion object {
            const val USER_NAME = "m_user_name"                    //用户账号
            const val NAME = "name"
            const val MPASSWORD = "m_password"                //用户密码
            const val NICK = "user_nick"                    //用户昵称
            const val PASSWORD = "password"
            const val UID = "uid"
        }
    }

    interface Contact {
        companion object {
            const val M_UID = "m_uid"                    //主键
            const val O_UID = "o_uid"            //用户账号
        }
    }

    interface Student{
        companion object{
            const val NAME = "name"
            const val UID = "uid"
            const val SEX = "sex"
            const val BCLASS = "bclass"
            const val BDEPARTMENT = "bdepartment"
            const val BBEDROOM = "bbedroom"
            const val USER_NICK = "user_nick"
            const val MARK = "mark"
        }
    }

    interface Group {
        companion object {
            const val TABLE_NAME = "t_superwechat_group"
            const val GROUP_ID = "m_group_id"                    //主键
            const val HX_ID = "m_group_hxid"                    //环信群组id
            const val NAME = "m_group_name"                    //群组名称
            const val DESCRIPTION = "m_group_description"            //群组简介
            const val OWNER = "m_group_owner"                //群组所有者－用户账号
            const val MODIFIED_TIME = "m_group_last_modified_time"    //最后修改时间
            const val MAX_USERS = "m_group_max_users"            //最大人数
            const val AFFILIATIONS_COUNT = "m_group_affiliations_count"    //群组人数
            const val IS_PUBLIC = "m_group_is_public"            //群组是否公开
            const val ALLOW_INVITES = "m_group_allow_invites"        //是否可以邀请
        }
    }

    interface Member {
        companion object {
            const val TABLE_NAME = "t_superwechat_member"
            const val MEMBER_ID = "m_member_id"                    //主键
            const val USER_NAME = "m_member_user_name"            //用户账号
            const val GROUP_ID = "m_member_group_id"            //群组id
            const val GROUP_HX_ID = "m_member_group_hxid"            //群组环信id
            const val PERMISSION = "m_member_permission"            //用户对群组的权限\n0:普通用户\n1:群组所有者
        }
    }

    interface Avatar {
        companion object {
            const val TABLE_NAME = "t_superwechat_avatar"
            const val AVATAR_ID = "m_avatar_id"                    //主键
            const val USER_NAME = "m_avatar_user_name"            //用户账号或者群组账号
            const val AVATAR_SUFFIX = "m_avatar_suffix"              //头像后缀名
            const val AVATAR_PATH = "m_avatar_path"                //保存路径
            const val AVATAR_TYPE = "m_avatar_type"                //头像类型：\n0:用户头像\n1:群组头像
            const val UPDATE_TIME = "m_avatar_last_update_time"    //最后更新时间
        }
    }

    interface Location {
        companion object {
            const val TABLE_NAME = "t_superwechat_location"
            const val LOCATION_ID = "m_location_id"                //主键
            const val USER_NAME = "m_location_user_name"            //用户账号
            const val LATITUDE = "m_location_latitude"            //纬度
            const val LONGITUDE = "m_location_longitude"            //经度
            const val IS_SEARCHED = "m_location_is_searched"        //是否可以被搜索到
            const val UPDATE_TIME = "m_location_last_update_time"    //最后更新时间
        }
    }

    companion object {
        const val CLIENT = "cahn"
        const val OPT_USER = "optUser"
        const val REQUEST_KEY = "request"
        const val SERVER_ROOT = "http://115.29.33.91:8080/StudentManagerServer/"
        const val AVATAR_SERVER_ROOT = "http://115.29.33.91:8080/StudentManagerServer/avatar/"
        const val MANAGER_SERVER = "Server"
        const val JPGFORMAT = ".jpg"
        //  const val SERVER_ROOT = "http://101.251.196.90:8000/SuperWeChatServerV2.0/"
        /** 客户端发送的获取服务端状态的请求  */
        const val REQUEST_SERVERSTATUS = "GetServerState"
        /** 客户端发送的新用户注册的请求  */
        const val REQUEST_REGISTER = "Register"
        /** 客户端发送的取消注册的请求  */
        const val REQUEST_UNREGISTER = "unregister"
        /** 客户端发送的用户登录请求  */
        const val REQUEST_LOGIN = "Login"
        /** 客户端发送的下载用户头像请求  */
        const val REQUEST_DOWNLOAD_AVATAR = "downloadAvatar"
        /** 客户端发送的上传/更新用户头像的请求  */
        const val REQUEST_UPDATE_AVATAR = "updateAvatar"
        /** 客户端发送的更新用户昵称的请求  */
        const val REQUEST_UPDATE_USER_NICK = "updateNick"
        /** 客户端发送的更新用户密码的请求  */
        const val REQUEST_UPDATE_USER_PASSWORD = "updatePassword"
        /** 客户端发送的下载用户的好友列表的全部数据的请求  */
        const val REQUEST_DOWNLOAD_CONTACT_ALL_LIST = "DownloadAllContact"
        /** 客户端发送的添加好友的请求  */
        const val REQUEST_ADD_CONTACT = "AddContact"
        /** 客户端发送的删除好友的请求  */
        const val REQUEST_DELETE_CONTACT = "deleteContact"
        /** 客户端发送的根据用户名查找用户信息的请求  */
        const val REQUEST_FIND_USER = "findUserByUserName"
        const val REQUEST_GET_ALL_CLASS = "getClass"
        const val REQUEST_GET_ALL_DEPARTMENT = "getDepartment"
        const val REQUEST_GET_ALL_BEDROOM = "getBedRoomInfo"
        const val REQUEST_UPDATE_USER = "modStuInfo"
        const val REQUEST_GET_USER_BY_NICK = "FindUserByNick"
        const val REQUEST_GET_USERS_BY_NICK = "GetInfosByNick"
    }
}
