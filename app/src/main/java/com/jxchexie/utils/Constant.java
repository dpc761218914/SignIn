package com.jxchexie.utils;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Constant {

        public static final String SUCCESS="success"; //成功
        public static final String ERROR="error"; //失败
        public static final String ERROR_SYSTEM="error_system"; //系统错误
        public static final String ERROR_USERNAME="error_username"; //用户名错误
        public static final String ERROR_PASSWORD="error_password"; //密码错误
        public static final String ERROR_ALREADY_SIGNIN="already_signin"; //签过了
        public static final String ERROR_USER_EXIST="user_exist"; //用户已存在

        /*上午下午晚上签到类型标志位*/
        public static final String AM_SIGNIN="am_signin";
        public static final String AM_SIGNOUT="am_signout";
        public static final String PM_SIGNIN="pm_signin";
        public static final String PM_SIGNOUT="pm_signout";
        public static final String NIGHT_SIGNIN="night_signin";
        public static final String NIGHT_SIGNOUT="night_signout";

        /*关于E520和E412路由器的Mac地址*/
        public static final String E520_MAC="";
        public static final String E412_MAC="f4:83:cd:0b:b8:68";

        /*设置超时时间5秒钟*/
        public static final int MY_MILLISECONDS = 5000;       //默认的超时时间




}
