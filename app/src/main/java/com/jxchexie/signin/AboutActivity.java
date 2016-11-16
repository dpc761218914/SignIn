package com.jxchexie.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

/*设置页面*/
public class AboutActivity extends AppCompatActivity {

    private LinearLayout linear_thanks;
    private LinearLayout linear_author;
    private Toolbar aboutToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initEvent();
    }

    private void initView(){
        linear_thanks=(LinearLayout) findViewById(R.id.linear_thanks);
        linear_author=(LinearLayout) findViewById(R.id.linear_author);
        aboutToolbar=(Toolbar)findViewById(R.id.aboutToolbar);
        aboutToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.this.finish();/*关闭当前页*/
            }
        });

    }
    private void initEvent(){
        linear_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("感谢开源项目及作者",R.string.thanks_open_source);
            }
        });

        linear_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("关于",R.string.about_me);
            }
        });
    }


    /*页面跳转*/
    public void toActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    /*签到提示对话框*/
    public void showMessage(String tip,int msg){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(tip)
                .content(msg)
                .positiveText("确定")
                .show();
    }



}
