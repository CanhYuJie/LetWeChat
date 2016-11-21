package com.hyphenate.easeui.model;

import java.io.Serializable;

/**
 * Created by yujie on 16-11-21.
 */

public class ResultData implements Serializable {


    /**
     * flag : true
     * msg : 获取成功
     * data : {"name":"刘虞桀","uid":"10601142031","sex":"男","b_class":"计算机信息管理14-2班","b_department":"计算机科学与技术系","b_bedroom":"1A419","user_nick":"aizen","blackList":null,"password":null}
     */

    private boolean flag;
    private String msg;
    /**
     * name : 刘虞桀
     * uid : 10601142031
     * sex : 男
     * b_class : 计算机信息管理14-2班
     * b_department : 计算机科学与技术系
     * b_bedroom : 1A419
     * user_nick : aizen
     * blackList : null
     * password : null
     */

    private DataBean data;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String name;
        private String uid;
        private String sex;
        private String b_class;
        private String b_department;
        private String b_bedroom;
        private String user_nick;
        private Object blackList;
        private Object password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getB_class() {
            return b_class;
        }

        public void setB_class(String b_class) {
            this.b_class = b_class;
        }

        public String getB_department() {
            return b_department;
        }

        public void setB_department(String b_department) {
            this.b_department = b_department;
        }

        public String getB_bedroom() {
            return b_bedroom;
        }

        public void setB_bedroom(String b_bedroom) {
            this.b_bedroom = b_bedroom;
        }

        public String getUser_nick() {
            return user_nick;
        }

        public void setUser_nick(String user_nick) {
            this.user_nick = user_nick;
        }

        public Object getBlackList() {
            return blackList;
        }

        public void setBlackList(Object blackList) {
            this.blackList = blackList;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }
    }
}
