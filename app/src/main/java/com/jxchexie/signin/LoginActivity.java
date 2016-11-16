package com.jxchexie.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.CheckBox;
import com.jxchexie.api.Api;
import com.jxchexie.bean.ResponseLogin;
import com.jxchexie.utils.Constant;
import com.jxchexie.utils.JsonUtils;
import com.jxchexie.utils.PreferencesUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {


    private ButtonRectangle btn_login;
    private MaterialEditText username;
    private MaterialEditText password;
    private String str_username;
    private String str_password;
    private MaterialDialog md;
    private ResponseLogin login;
    private ButtonFlat btn_toRegister;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView(){
        btn_login=(ButtonRectangle)findViewById(R.id.btn_login);
        username=(MaterialEditText)findViewById(R.id.username);
        password=(MaterialEditText)findViewById(R.id.password);
        btn_toRegister=(ButtonFlat)findViewById(R.id.btn_toRegister);
        checkBox=(CheckBox) findViewById(R.id.checkBox);
        //设置默认记住密码
        checkBox.setChecked(true);
        /*如果SP里面保存了用户名密码，就将它取出来*/
        str_username=PreferencesUtils.getString(LoginActivity.this,"username");
        str_password=PreferencesUtils.getString(LoginActivity.this,"password");
        if(!StringUtils.isEmpty(str_username)){
            username.setText(str_username);
            password.setText(str_password);
        }
    }
    private void initEvent(){
        /*登录*/
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });
        /*注册*/
        btn_toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(RegisterActivity.class);
            }
        });
    }
    /*用户名密码校验*/
    public void checkUser(){
        /*判断网络是否连接*/
        if(!NetworkUtils.isConnected(LoginActivity.this)){
            Toast.makeText(LoginActivity.this,"网络未连接...",Toast.LENGTH_SHORT).show();
            return;
        }
        str_username=username.getText().toString();
        str_password=password.getText().toString();
        if(StringUtils.isSpace(str_username)){
            Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isSpace(str_password)){
            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        /*显示提示正在登录对话框*/
        md=new MaterialDialog.Builder(this)
                .title("提示")
                .content("正在登录...")
                .progress(true, 0)
                .show();

        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("username", str_username);
        params.put("password", str_password);
        JSONObject jsonObject = new JSONObject(params);
        /*发送登录请求*/
        OkGo.post(Api.LOGIN)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        /* int code = conn.getResponseCode();//返回码200请求成功，如果请求码不是200，则提示服务器出错*/

                        login=new ResponseLogin();
                        login= JsonUtils.fromJson(s,ResponseLogin.class);
                        md.dismiss();
                        if(login.getStatus().equals(Constant.SUCCESS)){

                            /*如果勾选了记住密码，且登录成功，就保存用户名密码*/
                            if(checkBox.isCheck()){
                              /*记住用户名密码*/
                                PreferencesUtils.putString(LoginActivity.this,"username",str_username);
                                PreferencesUtils.putString(LoginActivity.this,"password",str_password);
                            }
                            toActivity(MainActivity.class);
                            Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            LoginActivity.this.finish();
                        }else{
                            if(login.getMsg().equals(Constant.ERROR_SYSTEM)){
                                Toast.makeText(LoginActivity.this,"系统错误",Toast.LENGTH_SHORT).show();
                                return;
                            }if(login.getMsg().equals(Constant.ERROR_USERNAME)){
                                Toast.makeText(LoginActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                return;
                            }if(login.getMsg().equals(Constant.ERROR_PASSWORD)){
                                Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });
    }
    /*页面跳转*/
    public void toActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }


}
