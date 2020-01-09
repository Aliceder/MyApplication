package com.example.administrator.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.mmvtc.practicaltraining.R;
import cn.mmvtc.practicaltraining.SQL.SQLHelper;
import cn.mmvtc.practicaltraining.Adapter.shoping02Adapter;

public class Shopping02Activity extends AppCompatActivity implements View.OnClickListener {
    SQLHelper sqlHelper;
    private List<String> dataList;
    private shoping02Adapter adapter;
    private TextView  tv_title,shop_cart02,shop_cart03;
    List<String> list=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping02);
        sqlHelper = new SQLHelper(this);
        shop_cart02=(TextView) findViewById(R.id.shop_cart02);
        shop_cart02.setOnClickListener(this);
        shop_cart03=(TextView) findViewById(R.id.shop_cart03);
        shop_cart03.setOnClickListener(this);

        query();
        //获取lv 并设置适配器
        ListView listView = (ListView) findViewById(R.id.Content);
        //创建适配器，传递数据集合，以及条目中被点击控件的的点击监听
        adapter = new shoping02Adapter(Shopping02Activity.this, dataList);
        listView.setAdapter(adapter);
    }

    //查询所有商品
    public void query(){
        SQLiteDatabase db;
        db=sqlHelper.getReadableDatabase();
        //初始化数据
        dataList = new ArrayList<>();
        tv_title=(TextView) findViewById(R.id.tv_title);
        Cursor cursor=db.query("Shop",null,null,null,null,null,null);
        if(cursor.getCount()==0){
            //dataList.add("");
            Toast.makeText(this, "没有商品", Toast.LENGTH_SHORT).show();
        }else{
            cursor.moveToFirst();
            dataList.add("\n"+"名称: "+cursor.getString(1)+"\n"+"价格: "+cursor.getString(2)+"\n");
            list.add(cursor.getString(0));
        }
        while(cursor.moveToNext()){
            dataList.add("\n"+"名称: "+cursor.getString(1)+"\n"+" 价格: "+cursor.getString(2)+"\n");
            list.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del:   //lv条目中 iv_del
                final int position = (int) v.getTag(); //获取被点击的控件所在item 的位置，setTag 存储的object，所以此处要强转
                //点击删除按钮之后，给出dialog提示
                AlertDialog.Builder builder = new AlertDialog.Builder(Shopping02Activity.this);
                builder.setTitle( "确定要删除该商品吗?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String n;
                        SQLiteDatabase db;
                        db=sqlHelper.getReadableDatabase();
                        //删除item条目
                        dataList.remove(position);
                        adapter.notifyDataSetChanged();
                        //删除数据库数据
                        n=list.get(position);
                        String[] contentValuseArray = new String[]{String.valueOf(n)};

                        //Toast.makeText(Shopping02Activity.this, n+"", Toast.LENGTH_SHORT).show();
                        db.delete("shop","_id=?",contentValuseArray);
                        list.remove(n);
                        db.close();

                    }
                });
                builder.show();
                break;

            case R.id.shop_cart02:
                Intent intent1=new Intent(Shopping02Activity.this,ShoppingActivity.class);
                startActivity(intent1);
                //清空数组
                for (int j=0;j<list.size();j++){
                    list.remove(j);
                }
                break;

            //清空购物车
            case R.id.shop_cart03:
                int i=0,n=0;
                SQLiteDatabase db=sqlHelper.getWritableDatabase();
                db.delete("shop",null,null);
                while (i<list.size()){
                    i++;
                    dataList.remove(n);
                }
                adapter.notifyDataSetChanged();
                //清空数组
                for (int j=0;j<list.size();j++){
                    list.remove(j);
                }
                Toast.makeText(this, "购物车已清空！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
