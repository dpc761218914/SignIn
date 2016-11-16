package com.jxchexie.signin;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jxchexie.api.Api;
import com.jxchexie.app.App;
import com.jxchexie.bean.ResponseLogin;
import com.jxchexie.utils.Constant;
import com.jxchexie.utils.JsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private ButtonRectangle btn_StartAm;
    private ButtonRectangle btn_EndAm;
    private ButtonRectangle btn_StartPm;
    private ButtonRectangle btn_EndPm;
    private ButtonRectangle btn_StartN;
    private ButtonRectangle btn_EndN;

    private FloatingActionButton actionA;
    private FloatingActionButton actionB;
    private FloatingActionButton actionC;

    private ResponseLogin login;
    private MaterialDialog md;
    private String str_mac;
    private TextView tv_signin;
    private Toolbar mainToolbar;

    private boolean quit = false; //设置退出标识



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView(){
        btn_StartAm=(ButtonRectangle)findViewById(R.id.btn_StartAm);
        btn_EndAm=(ButtonRectangle)findViewById(R.id.btn_EndAm);
        btn_StartPm=(ButtonRectangle)findViewById(R.id.btn_StartPm);
        btn_EndPm=(ButtonRectangle)findViewById(R.id.btn_EndPm);
        btn_StartN=(ButtonRectangle)findViewById(R.id.btn_StartN);
        btn_EndN=(ButtonRectangle)findViewById(R.id.btn_EndN);
        actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionC = (FloatingActionButton) findViewById(R.id.action_c);
        tv_signin=(TextView)findViewById(R.id.tv_signin);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd  EEE");
        String str_Date=TimeUtils.getCurTimeString(sdf);
        tv_signin.setText("签到日期: "+str_Date);
        mainToolbar=(Toolbar)findViewById(R.id.mainToolbar);

        mainToolbar.inflateMenu(R.menu.menu_main);
        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.action_settings){
                    toActivity(SettingActivity.class);
                }
                if(id==R.id.action_about){
                    toActivity(AboutActivity.class);
                }
                return false;
            }
        });
    }
    private void initEvent(){
        /*第一个悬浮菜单点击事件*/
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(HolidayActivity.class);
            }
        });
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(HolidayRecordActivity.class);
            }
        });
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(SignInRecordActivity.class);
            }
        });



        btn_StartAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkLabMac()){
                    return;
                }
                SignIn(Constant.AM_SIGNIN);
            }
        });

        btn_EndAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkLabMac()){
                    return;
                }
                SignIn(Constant.AM_SIGNOUT);
            }
        });

        btn_StartPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkLabMac()){
                    return;
                }
                SignIn(Constant.PM_SIGNIN);
            }
        });
        btn_EndPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkLabMac()){
                    return;
                }
                SignIn(Constant.PM_SIGNOUT);
            }
        });
        btn_StartN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkLabMac()){
                    return;
                }
                SignIn(Constant.NIGHT_SIGNIN);
            }
        });
        btn_EndN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkLabMac()){
                    return;
                }
                SignIn(Constant.NIGHT_SIGNOUT);
            }
        });
    }

    /*签到提示对话框*/
    public void signSuccess(String msg){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("提示")
                .content(msg)
                .positiveText("确定")
                .show();
    }


    /*页面跳转*/
    public void toActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
    /*显示正在加载对话框*/
    public void showWaitDialog(){
         /*显示提示正在签到对话框*/
        md=new MaterialDialog.Builder(this)
                .title("提示")
                .content("正在签到...")
                .progress(true, 0)
                .show();
    }
    /*比对路由器的MAC地址*/
    public boolean checkLabMac(){
        //获取WiFIMac地址
        if(NetworkUtils.isWifiConnected(MainActivity.this)){
            WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            str_mac=info.getBSSID();
            if(!str_mac.equals(Constant.E412_MAC)){
                signSuccess("非实验室WIFI,无法签到！");
                return false;
            }else {
                return true;
            }
        }else{
            signSuccess("WIFI未连接，请先连接实验室WIFI！");
            return false;
        }
    }
    public void SignIn(String type){
        showWaitDialog();
        /*获取当前时间*/
        java.text.SimpleDateFormat ss = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        /*主要是为了拼凑unique_id*/
        java.text.SimpleDateFormat ss2 = new java.text.SimpleDateFormat("yyyyMMdd");
        String date= TimeUtils.getCurTimeString(ss);
        String date2= TimeUtils.getCurTimeString(ss2);

        /*获得当前登录用户的用户名*/
        String user_name=App.getInstance().getUser(MainActivity.this);
         /*没人每天只有一个id*/
        String unique_id=user_name+date2;

        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("unique_id", unique_id);
        params.put("type", type);
        params.put("date", date);
        params.put("user_name",user_name);
        JSONObject jsonObject = new JSONObject(params);
                /*发送登录请求*/
        OkGo.post(Api.SIGNIN)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Log.e("Mainactivity____>", response.code()+"状态码："+call.toString() );
                        if(response.code()!=200){
                             /*打开签到成功提示对话框*/
                            signSuccess("后台维护中...");
                            return;
                        }
                        login=new ResponseLogin();
                        login= JsonUtils.fromJson(s,ResponseLogin.class);
                        if(login.getStatus().equals(Constant.SUCCESS)){
                            /*关闭加载对话框*/
                            md.dismiss();
                            /*打开签到成功提示对话框*/
                            signSuccess("签到成功");
                        }else if(login.getMsg().equals(Constant.ERROR_SYSTEM)){
                             /*关闭加载对话框*/
                            md.dismiss();
                            /*打开签到成功提示对话框*/
                            signSuccess("系统发生错误，请联系程序开发者");
                        }else if(login.getMsg().equals(Constant.ERROR_ALREADY_SIGNIN)){
                             /*关闭加载对话框*/
                            md.dismiss();
                            /*打开签到成功提示对话框*/
                            signSuccess("你已经签过了..请勿重复签！");
                        }
                    }
                });
    }
    /*双击退出程序*/
    @Override
    public void onBackPressed(){
        if(quit==false){//询问是否退出程序
            Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            new Timer(true).schedule(new TimerTask() {
                @Override
                public void run() {
                    quit=false;
                }
            },2000);
            quit=true;
        }else {
            super.onBackPressed();
            finish();
        }
    }

}
