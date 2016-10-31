package com.yujie.letwechat

interface I {

    interface User {
        companion object {
            val TABLE_NAME = "t_superwechat_user"
            val USER_NAME = "m_user_name"                    //用户账号
            val PASSWORD = "m_user_password"                //用户密码
            val NICK = "m_user_nick"                    //用户昵称
        }
    }

    interface Contact {
        companion object {
            val TABLE_NAME = "t_superwechat_contact"
            val CONTACT_ID = "m_contact_id"                    //主键
            val USER_NAME = "m_contact_user_name"            //用户账号
            val CU_NAME = "m_contact_cname"                //好友账号
        }
    }

    interface Group {
        companion object {
            val TABLE_NAME = "t_superwechat_group"
            val GROUP_ID = "m_group_id"                    //主键
            val HX_ID = "m_group_hxid"                    //环信群组id
            val NAME = "m_group_name"                    //群组名称
            val DESCRIPTION = "m_group_description"            //群组简介
            val OWNER = "m_group_owner"                //群组所有者－用户账号
            val MODIFIED_TIME = "m_group_last_modified_time"    //最后修改时间
            val MAX_USERS = "m_group_max_users"            //最大人数
            val AFFILIATIONS_COUNT = "m_group_affiliations_count"    //群组人数
            val IS_PUBLIC = "m_group_is_public"            //群组是否公开
            val ALLOW_INVITES = "m_group_allow_invites"        //是否可以邀请
        }
    }

    interface Member {
        companion object {
            val TABLE_NAME = "t_superwechat_member"
            val MEMBER_ID = "m_member_id"                    //主键
            val USER_NAME = "m_member_user_name"            //用户账号
            val GROUP_ID = "m_member_group_id"            //群组id
            val GROUP_HX_ID = "m_member_group_hxid"            //群组环信id
            val PERMISSION = "m_member_permission"            //用户对群组的权限\n0:普通用户\n1:群组所有者
        }
    }

    interface Avatar {
        companion object {
            val TABLE_NAME = "t_superwechat_avatar"
            val AVATAR_ID = "m_avatar_id"                    //主键
            val USER_NAME = "m_avatar_user_name"            //用户账号或者群组账号
            val AVATAR_SUFFIX = "m_avatar_suffix"              //头像后缀名
            val AVATAR_PATH = "m_avatar_path"                //保存路径
            val AVATAR_TYPE = "m_avatar_type"                //头像类型：\n0:用户头像\n1:群组头像
            val UPDATE_TIME = "m_avatar_last_update_time"    //最后更新时间
        }
    }

    interface Location {
        companion object {
            val TABLE_NAME = "t_superwechat_location"
            val LOCATION_ID = "m_location_id"                //主键
            val USER_NAME = "m_location_user_name"            //用户账号
            val LATITUDE = "m_location_latitude"            //纬度
            val LONGITUDE = "m_location_longitude"            //经度
            val IS_SEARCHED = "m_location_is_searched"        //是否可以被搜索到
            val UPDATE_TIME = "m_location_last_update_time"    //最后更新时间
        }
    }

    companion object {

        //	String AVATAR_PATH 								= 		"E:/test/";
        val ISON8859_1 = "iso8859-1"
        val UTF_8 = "utf-8"
        val PAGE_ID = "page_id"                        //分页的起始下标
        val PAGE_SIZE = "page_size"                    //分页的每页数量
        val DEFAULT_DISTANCE = 100                            //查找附近的人时，默认距离为100KM
        val ID_DEFAULT = 0                                //ID默认值
        val UN_READ_MSG_COUNT_DEFAULT = 0                                //未读消息数量默认值
        val GROUP_MAX_USERS_DEFAULT = -1                                //群组最大人数默认值
        val GROUP_AFFILIATIONS_COUNT_DEFAULT = 1                                //群组人数默认值
        val PERMISSION_NORMAL = 0                                //普通用户群组权限
        val PERMISSION_OWNER = 1                                //群组所有者群组权限
        val AVATAR_TYPE_USER = 0                                //用户头像
        val AVATAR_TYPE_GROUP = 1                                //群组头像
        val GROUP_PUBLIC = 1                                //公开群组
        val GROUP_NO_PUBLIC = 0                                //非公开群组
        val AVATAR_TYPE_USER_PATH = "user_avatar"                    //用户头像保存目录
        val AVATAR_TYPE_GROUP_PATH = "group_icon"                    //群组头像保存目录
        val AVATAR_SUFFIX_PNG = ".png"                            //PNG图片后缀名
        val AVATAR_SUFFIX_JPG = ".jpg"                            //JPG图片后缀名
        val AVATAR_WIDTH = "width"                        //下载图片的宽度
        val AVATAR_HEIGHT = "height"                        //下载图片的高度
        val AVATAR_WIDTH_DEFAULT = 200                            //下载图片的宽度默认值
        val AVATAR_HEIGHT_DEFAULT = 200                            //下载图片的高度默认值
        val QUESTION = "?"                            //问号
        val EQUAL = "="                            //等号
        val AND = "&"                            //&符号
        val MSG_PREFIX_MSG = "msg_"                         //消息码前缀
        val LOCATION_IS_SEARCH_ALLOW = 1                                //可以被搜索到地理位置
        val LOCATION_IS_SEARCH_INHIBIT = 0                                //禁止被搜索到地理位置
        val MSG_SUCCESS = 0                                //默认成功
        val MSG_REGISTER_USERNAME_EXISTS = 101                            //账号已经存在
        val MSG_REGISTER_FAIL = 102                            //注册失败
        val MSG_UNREGISTER_FAIL = 103                            //解除注册失败
        val MSG_USER_SEARCH_FAIL = 104                            //查找用户失败
        val MSG_LOGIN_UNKNOW_USER = 105                            //账户不存在
        val MSG_LOGIN_ERROR_PASSWORD = 106                            //账户密码错误
        val MSG_LOGIN_SUCCESS = 107                            //登陆成功
        val MSG_USER_SAME_NICK = 108                            //昵称未修改
        val MSG_USER_UPDATE_NICK_FAIL = 109                            //昵称修改失败
        val MSG_USER_SAME_PASSWORD = 110                            //昵称未修改
        val MSG_USER_UPDATE_PASSWORD_FAIL = 111                            //昵称修改失败
        val MSG_LOCATION_UPLOAD_FAIL = 112                            //用户上传地理位置失败
        val MSG_LOCATION_UPDATE_FAIL = 113                            //用户更新地理位置失败
        val MSG_REGISTER_UPLOAD_AVATAR_FAIL = 201                            //上传头像失败
        val MSG_UPLOAD_AVATAR_FAIL = 202                            //更新头像失败
        val MSG_CONTACT_FIRENDED = 301                            //已经是好友关系
        val MSG_CONTACT_ADD_FAIL = 302                            //好友关系添加失败
        val MSG_CONTACT_DEL_FAIL = 303                            //好友关系删除失败
        val MSG_GET_CONTACT_ALL_FAIL = 304                            //获取全部好友列表失败
        val MSG_GET_CONTACT_PAGES_FAIL = 305                            //分页获取好友列表失败
        val MSG_GROUP_HXID_EXISTS = 401                            //群组环信ID已经存在
        val MSG_GROUP_CREATE_FAIL = 402                            //创建群组失败
        val MSG_GROUP_ADD_MEMBER_FAIL = 403                            //添加群组成员失败
        val MSG_GROUP_GET_MEMBERS_FAIL = 404                            //获取群成员失败
        val MSG_GROUP_UNKONW = 405                            //群组不存在
        val MSG_GROUP_SAME_NAME = 406                            //有相同群组名称
        val MSG_GROUP_UPDATE_NAME_FAIL = 407                            //群组名称修改失败
        val MSG_GROUP_DELETE_MEMBER_FAIL = 408                            //删除群组成员失败
        val MSG_GROUP_DELETE_MEMBERS_FAIL = 409                            //删除多群组成员失败
        val MSG_GROUP_DELETE_FAIL = 410                            //删除群组失败
        val MSG_GROUP_FIND_BY_GOURP_ID_FAIL = 411                            //根据群组id查找群组失败
        val MSG_GROUP_FIND_BY_HX_ID_FAIL = 412                            //根据环信id查找群组失败
        val MSG_GROUP_FIND_BY_USER_NAME_FAIL = 413                            //查找用户名称查找查找群组失败
        val MSG_GROUP_FIND_BY_GROUP_NAME_FAIL = 414                            //查找群组名称查找查找群组失败
        val MSG_PUBLIC_GROUP_FAIL = 415                            //查找公开群失败
        val MSG_LOCATION_GET_FAIL = 501                            //获取附近的人失败
        val MSG_UNKNOW = 999                            //未知错误
        val MSG_ILLEGAL_REQUEST = -1                                //非法请求

        //	String SERVER_ROOT = "http://120.26.242.249:8080/SuperWeChatServerV2.0/";
        val SERVER_ROOT = "http://101.251.196.90:8000/SuperWeChatServerV2.0/"

        /** 上传头像图片的类型：user_avatar或group_icon  */
        val AVATAR_TYPE = "avatarType"
        /** 用户的账号或群组的环信id  */
        val NAME_OR_HXID = "name_or_hxid"
        /** 客户端发送的获取服务端状态的请求  */
        val REQUEST_SERVERSTATUS = "getServerStatus"
        /** 客户端发送的新用户注册的请求  */
        val REQUEST_REGISTER = "register"
        /** 客户端发送的取消注册的请求  */
        val REQUEST_UNREGISTER = "unregister"
        /** 客户端发送的用户登录请求  */
        val REQUEST_LOGIN = "login"
        /** 客户端发送的下载用户头像请求  */
        val REQUEST_DOWNLOAD_AVATAR = "downloadAvatar"
        /** 客户端发送的上传/更新用户头像的请求  */
        val REQUEST_UPDATE_AVATAR = "updateAvatar"
        /** 客户端发送的更新用户昵称的请求  */
        val REQUEST_UPDATE_USER_NICK = "updateNick"
        /** 客户端发送的更新用户密码的请求  */
        val REQUEST_UPDATE_USER_PASSWORD = "updatePassword"
        /** 客户端发送的下载用户的好友列表的全部数据的请求  */
        val REQUEST_DOWNLOAD_CONTACT_ALL_LIST = "downloadContactAllList"
        /** 客户端发送的分页下载用户的好友列表的全部数据的请求  */
        val REQUEST_DOWNLOAD_CONTACT_PAGE_LIST = "downloadContactPageList"
        /** 客户端发送的添加好友的请求  */
        val REQUEST_ADD_CONTACT = "addContact"
        /** 客户端发送的删除好友的请求  */
        val REQUEST_DELETE_CONTACT = "deleteContact"
        /** 客户端发送的根据用户名查找用户信息的请求  */
        val REQUEST_FIND_USER = "findUserByUserName"
        /** 客户端发送的根据用户名或昵称模糊分页查找用户数据的请求  */
        val REQUEST_FIND_USERS_FOR_SEARCH = "findUsersForSearch"
        /** 客户端发送的创建群组的请求  */
        val REQUEST_CREATE_GROUP = "createGroup"
        /** 客户端发送的更新群组名称的请求  */
        val REQUEST_UPDATE_GROUP_NAME = "updateGroupName"
        /** 客户端发送的添加群成员的请求  */
        val REQUEST_ADD_GROUP_MEMBER = "addGroupMember"
        /** 客户端发送的批量添加群成员的请求  */
        val REQUEST_ADD_GROUP_MEMBERS = "addGroupMembers"
        /** 客户端发送的根据群组ID下载全部群成员信息的请求  */
        val REQUEST_DOWNLOAD_GROUP_MEMBERS = "downloadGroupMembersByGroupId"
        /** 客户端发送的根据群组ID分页下载群成员信息的请求  */
        val REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_LIMIT = "downloadGroupMembersPagesByGroupId"
        /** 客户端发送的根据群组环信ID下载全部群成员信息的请求  */
        val REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID = "downloadGroupMembersByHxId"
        /** 客户端发送的根据群组环信ID分页下载群成员信息的请求  */
        val REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID_LIMIT = "downloadGroupMembersPagesByHxId"
        /** 客户端发送的删除单个群成员的请求  */
        val REQUEST_DELETE_GROUP_MEMBER = "deleteGroupMember"
        /** 客户端发送的删除多个群成员的请求  */
        val REQUEST_DELETE_GROUP_MEMBERS = "deleteGroupMembers"
        /** 客户端发送的删除群组的请求  */
        val REQUEST_DELETE_GROUP = "deleteGroup"
        /** 客户端发送的根据环信ID删除群组的请求  */
        val REQUEST_DELETE_GROUP_BY_HXID = "deleteGroupByHxid"
        /** 客户端发送的获取指定用户的群组全部数据列表的请求  */
        val REQUEST_FIND_GROUP_BY_USER_NAME = "findAllGroupByUserName"
        /** 客户端发送的分页下载公开群组列表的请求  */
        val REQUEST_FIND_PUBLIC_GROUPS = "findPublicGroups"
        /** 客户端发送的根据群组名称模糊查询群组信息的请求  */
        val REQUEST_FIND_GROUP_BY_GROUP_NAME = "findGroupByGroupName"
        /** 客户端发送的根据群组ID查找群组信息的请求  */
        val REQUEST_FIND_GROUP_BY_ID = "findGroupByGroupId"
        /** 客户端发送的根据群组环信ID查找群组信息的请求  */
        val REQUEST_FIND_GROUP_BY_HXID = "findGroupByHxId"
        /** 客户端发送的根据群组环信id查找公开群组的请求  */
        val REQUEST_FIND_PUBLIC_GROUP_BY_HXID = "findPublicGroupByHxId"
        /** 客户端发送的上传用户地理位置信息的请求  */
        val REQUEST_UPLOAD_LOCATION = "uploadLocation"
        /** 客户端发送的更新用户地理位置信息的请求  */
        val REQUEST_UPDATE_LOCATION = "updateLocation"
        /** 客户端发送的分页下载附近的人的请求  */
        val REQUEST_DOWNLOAD_LOCATION = "downloadLocation"
    }
}
