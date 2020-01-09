package com.example.administrator.myapplication.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.SQL.LoginMyHelper;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    LoginMyHelper loginsql;
    SQLiteDatabase db;
    ContentValues values;
    private EditText et_account,et_password;
    private Button btn_login,btn_memory;
    private  String  name,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginsql=new LoginMyHelper(this);
    }

    private void initView(){
        et_account =(EditText) findViewById(R.id.et_account);
        et_password =(EditText) findViewById(R.id.et_password);
        btn_login =(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_memory =(Button)findViewById(R.id.btn_memory);
        btn_memory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                String Name,Pwd;
                name=et_account.getText().toString().trim();
                password=et_password.getText().toString().trim();
                db = loginsql.getWritableDatabase();
                Cursor cursor=db.query("login",null,null,null,null,null,null);
                if(cursor.getCount()==0){
                    Toast.makeText(this, "请注册账号！", Toast.LENGTH_SHORT).show();
                }else{
                    while (cursor.moveToNext()){
                        if(name.equals(cursor.getString(1)) && password.equals(cursor.getString(2))){
                            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this, ShoppingActivity.class);
                            startActivity(intent);
                            return;
                        }else{
                            Toast.makeText(this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                break;

            case R.id.btn_memory:
                name=et_account.getText().toString().trim();
                password=et_password.getText().toString().trim();
                db = loginsql.getWritableDatabase();
                values=new ContentValues();
                values.put("name",name);
                values.put("password",password);
                db.insert("login",null,values);
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                db.close();
                break;
        }
    }
}
