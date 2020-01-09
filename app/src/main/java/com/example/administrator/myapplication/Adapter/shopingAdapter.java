package com.example.administrator.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.mmvtc.practicaltraining.R;

public class shopingAdapter extends BaseAdapter {

    //添加点击监听
    private final View.OnClickListener listener;
    private final List<String> addList;

    private String[] titles = {"苹果", "蛋糕", "猫", "鹿", "猕猴桃", "丝巾", "哈士奇", "桌子", "老虎", "毛衣", "小黄鸭"};
    private String[] prices = {"2元", "120元", "100元", "1000元", "2元", "20元", "1111元", "50元", "1111元", "80元", "1元"};
    private int[] images = {R.drawable.apple, R.drawable.cake, R.drawable.cat, R.drawable.fawn, R.drawable.kiwifruit, R.drawable.scarf, R.drawable.siberiankusky, R.drawable.table, R.drawable.tiger, R.drawable.wireclothes, R.drawable.yellowduck};

    public shopingAdapter(View.OnClickListener listener, List<String> addList) {
        this.listener = listener;
        this.addList = addList;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.shopName = (TextView) convertView.findViewById(R.id.shop_name);
            holder.shopPrice = (TextView) convertView.findViewById(R.id.price);
            holder.images = (ImageView) convertView.findViewById(R.id.img);
            holder.add = (ImageView) convertView.findViewById(R.id.add);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.shopName.setText(titles[position]);
        holder.shopPrice.setText(prices[position]);
        holder.images.setBackgroundResource(images[position]);

        //给要被点击的控件加入点击监听，具体事件在创建adapter的地方实现
        holder.add.setOnClickListener(listener);
        //通过setTag 将被点击控件所在条目的位置传递出去
        holder.add.setTag(position);
        return convertView;
    }

        class ViewHolder {
        TextView shopName,shopPrice;
        ImageView images,add;
    }
}