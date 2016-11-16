package com.jxchexie.signin;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jxchexie.adapter.ListHolidayRecordAdapter;
import com.jxchexie.adapter.ListSignInRecordAdapter;
import com.jxchexie.api.Api;
import com.jxchexie.app.App;
import com.jxchexie.bean.Holiday;
import com.jxchexie.bean.ResponseHolidayRecord;
import com.jxchexie.bean.ResponseSigninRecord;
import com.jxchexie.bean.SignIn;
import com.jxchexie.utils.Constant;
import com.jxchexie.utils.JsonUtils;
import com.jxchexie.widget.EndLessOnScrollListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class SignInRecordActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recycler_signin;
    private SwipeRefreshLayout refresh_signin;

    private ArrayList<SignIn> datas;
    private Toolbar SignRecordToolbar;
    private ResponseSigninRecord responseSigninRecord;
    private ListSignInRecordAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_record);
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        recycler_signin=(RecyclerView) findViewById(R.id.recycler_signin);
        refresh_signin=(SwipeRefreshLayout)findViewById(R.id.refresh_signin);
        refresh_signin.setOnRefreshListener(this);
        SignRecordToolbar=(Toolbar)this.findViewById(R.id.SignRecordToolbar);
        SignRecordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInRecordActivity.this.finish();/*关闭当前页*/
            }
        });
        datas=new ArrayList<SignIn>();

    }
    /*获取网络数据*/
    private void initData(){
         /*获得当前登录用户的用户名*/
        String user_name= App.getInstance().getUser(SignInRecordActivity.this);
        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("page_num", "1");
        JSONObject jsonObject = new JSONObject(params);
        /*获取请假记录*/
        OkGo.post(Api.GET_SIGNINS)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        responseSigninRecord=new ResponseSigninRecord();
                        responseSigninRecord= JsonUtils.fromJson(s,ResponseSigninRecord.class);
                        if(responseSigninRecord.getStatus().equals(Constant.SUCCESS)){
                            datas.addAll(responseSigninRecord.getMsg());
                            if(datas==null || datas.size()<=0){
                                Toast.makeText(SignInRecordActivity.this,"无签到记录...",Toast.LENGTH_SHORT).show();
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(SignInRecordActivity.this,"系统错误,请稍后再试...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
    private void initEvent() {
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        recycler_signin.setLayoutManager(mLayoutManager);
        adapter=new ListSignInRecordAdapter(datas,SignInRecordActivity.this);
        recycler_signin.setAdapter(adapter);
        recycler_signin.setItemAnimator(new DefaultItemAnimator());
        recycler_signin.addOnScrollListener(new EndLessOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData(currentPage);
            }
        });
    }

    public void loadMoreData(int currentPage){
        /*获得当前登录用户的用户名*/
        String user_name= App.getInstance().getUser(SignInRecordActivity.this);
        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("page_num", currentPage+1+"");
        JSONObject jsonObject = new JSONObject(params);
        /*获取请假记录*/
        OkGo.post(Api.GET_HOLIDAYS)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        responseSigninRecord=new ResponseSigninRecord();
                        responseSigninRecord= JsonUtils.fromJson(s,ResponseSigninRecord.class);
                        if(responseSigninRecord.getStatus().equals(Constant.SUCCESS)){
                            datas.addAll(responseSigninRecord.getMsg());
                            if(datas==null || datas.size()<=0){
                                Toast.makeText(SignInRecordActivity.this,"无签到记录...",Toast.LENGTH_SHORT).show();
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(SignInRecordActivity.this,"系统错误,请稍后再试...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }

    private Handler mHandler = new Handler();
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            refresh_signin.setRefreshing(false);
                            Toast.makeText(SignInRecordActivity.this,"刷新成功,没有新数据",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
