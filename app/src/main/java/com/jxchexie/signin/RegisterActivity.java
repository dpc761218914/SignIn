package com.jxchexie.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.gc.materialdesign.views.ButtonRectangle;
import com.jxchexie.api.Api;
import com.jxchexie.bean.ResponseLogin;
import com.jxchexie.utils.Constant;
import com.jxchexie.utils.JsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {


    private ButtonRectangle btn_register;
    private MaterialEditText username_register;
    private MaterialEditText password_register;
    private String str_username;
    private String str_password;
    private MaterialDialog md;
    private ResponseLogin login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initView(){
        btn_register=(ButtonRectangle)findViewById(R.id.btn_register);
        username_register=(MaterialEditText)findViewById(R.id.username_register);
        password_register=(MaterialEditText)findViewById(R.id.password_register);
    }
    private void initEvent(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

    }
    /*用户名密码校验*/
    public void checkUser(){
        /*判断网络是否连接*/
        if(!NetworkUtils.isConnected(RegisterActivity.this)){
            Toast.makeText(RegisterActivity.this,"网络未连接...",Toast.LENGTH_SHORT).show();
            return;
        }
        str_username=username_register.getText().toString();
        str_password=password_register.getText().toString();
        if(StringUtils.isSpace(str_username)){
            Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isSpace(str_password)){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        /*显示提示正在登录对话框*/
        md=new MaterialDialog.Builder(this)
                .title("提示")
                .content("注册中，请稍后...")
                .progress(true, 0)
                .show();

        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("username", str_username);
        params.put("password", str_password);
        JSONObject jsonObject = new JSONObject(params);
        /*发送登录请求*/
        OkGo.post(Api.REGISTER)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        login=new ResponseLogin();
                        login= JsonUtils.fromJson(s,ResponseLogin.class);
                        md.dismiss();
                        if(login.getStatus().equals(Constant.SUCCESS)){
                            RegisterActivity.this.finish();
                            Toast.makeText(RegisterActivity.this,"注册成功，请登录",Toast.LENGTH_SHORT).show();
                        }else{
                            if(login.getMsg().equals(Constant.ERROR_SYSTEM)){
                                Toast.makeText(RegisterActivity.this,"系统错误",Toast.LENGTH_SHORT).show();
                                return;
                            }if(login.getMsg().equals(Constant.ERROR_USER_EXIST)){
                                Toast.makeText(RegisterActivity.this,"用户名已被注册",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                });






    }
}
