package com.sky.crs.bean;

/*
 * @创建者     Administrator
 * @创建时间   2017/1/3 23:19
 * @描述	      ${TODO}
 */


import com.sky.crs.conf.Constant;
import com.sky.crs.net.Convert;
import com.sky.crs.util.Cxt;
import com.sky.crs.util.SPUtils;

public class LoginUserManger {


    public static LoginUserManger getInstance() {
        if (instance == null) {
            synchronized (LoginUserManger.class) {
                if (instance == null) {
                    instance = new LoginUserManger();
                }
            }
        }
        return instance;
    }

    public static String userName;

    private static boolean isAdmin;

    private static Admin admin;
    private static Student student;

    private static LoginUserManger instance;

    public Student getStudent() {
        try {
            if (student == null) {
                String string = SPUtils.getString(Cxt.get(), Constant.LOGIN.USER);
                student = Convert.fromJson(string, Student.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public Admin getAdmin() {
        try {
            if (admin == null) {
                String string = SPUtils.getString(Cxt.get(), Constant.LOGIN.USER);
                admin = Convert.fromJson(string, Admin.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public void setLoginUser(Object ob) {
        if (ob instanceof Admin) {
            admin = (Admin) ob;
            SPUtils.putString(Cxt.get(), Constant.LOGIN.USER, Convert.toJson(ob));
        } else if (ob instanceof Student) {
            student = (Student) ob;
            SPUtils.putString(Cxt.get(), Constant.LOGIN.USER, Convert.toJson(ob));
        }
    }


    public boolean getisAdmin() {
        boolean aBoolean = SPUtils.getBoolean(Cxt.get(), Constant.LOGIN.ISADMIN);
        if (aBoolean == isAdmin)
            return isAdmin;
        else
            return aBoolean;
    }

    public String getUserName() {
        String userName = SPUtils.getString(Cxt.get(), Constant.LOGIN.USERNAME);
        return userName;
    }


    public void setIsAmin(boolean isAdmin) {
        SPUtils.putBoolean(Cxt.get(), Constant.LOGIN.ISADMIN, isAdmin);
        this.isAdmin = isAdmin;
    }

    public void setUserName(String userName) {
        SPUtils.putString(Cxt.get(), Constant.LOGIN.USERNAME, userName);
        this.userName = userName;
    }
}
