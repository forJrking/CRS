package com.sky.crs.conf;

/*
 * @创建者     Administrator
 * @创建时间   2017/1/2 23:04
 * @描述	      ${TODO}

 */

public interface Constant {

    String CONN_ADDRESS = "address";
    int SUCCESS = 0;
    int ERROR = 1;

    public class Intent {

        public static String IS_NOTICE = "IS_NOTICE";
        public static String USERNAME = "username";
        public static String IS_UPDATE = "is_update";
    }

    public class LOGIN {

        public static String ISADMIN = "isadmin";
        public static String USER = "login_user";
        public static String USERNAME = "username";


    }

    public class FRAGMENT {
        public static String TYPE = "type";
    }
}
