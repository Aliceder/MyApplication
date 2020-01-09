package com.example.administrator.myapplication.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import cn.mmvtc.practicaltraining.Bean;
import cn.mmvtc.practicaltraining.R;
import cn.mmvtc.practicaltraining.SQL.SQLHelper;
import cn.mmvtc.practicaltraining.Adapter.shopingAdapter;

public class ShoppingActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView shop_cart,shopName,shopPrice;
    private shopingAdapter adapter;
    private ImageView add;
    ContentValues values;
    SQLiteDatabase db;
    private List<String> addList;
    SQLHelper sqlHelper;
    List<Bean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        sqlHelper = new SQLHelper(this);

        shop_cart=(TextView) findViewById(R.id.shop_cart);
        shop_cart.setOnClickListener(this);

        //获取lv 并设置适配器
        ListView listView = (ListView) findViewById(R.id.list_con);
        //创建适配器，传递数据集合，以及条目中被点击控件的的点击监听
        adapter = new shopingAdapter(ShoppingActivity.this,addList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db = sqlHelper.getWritableDatabase();
                values = new ContentValues();
                shopName= (TextView) view.findViewById(R.id.shop_name);
                shopPrice= (TextView) view.findViewById(R.id.price);
                add=(ImageView) view.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      String ShopName,ShopPrice;
                       ShopName=shopName.getText()+"";
                      ShopPrice=shopPrice.getText()+"";
                      values.put("name",ShopName);
                      values.put("price",ShopPrice);
                      db.insert("Shop",null,values);
                      Toast.makeText(ShoppingActivity.this, "商品添加成功，请点击购物车查看", Toast.LENGTH_SHORT).show();
                      db.close();
                  }
              });
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shop_cart:
                Intent intent=new Intent(ShoppingActivity.this, Shopping02Activity.class);
                startActivity(intent);
                break;
//            case R.id.add:
//                String ShopName,ShopPrice;
//                ShopName=shopName.getText()+"";
//                ShopPrice=shopPrice.getText()+"";
//                values.put("name",ShopName);
//                values.put("price",ShopPrice);
//                db.insert("Shop",null,values);
//                Toast.makeText(ShoppingActivity.this, "商品添加成功，请点击购物车查看", Toast.LENGTH_SHORT).show();
//                db.close();
//                break;
        }
    }


}
