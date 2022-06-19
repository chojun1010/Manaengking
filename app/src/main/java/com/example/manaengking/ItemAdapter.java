package com.example.manaengking;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/* 리스트뷰 어댑터 */
public class ItemAdapter extends BaseAdapter {
    ArrayList<ItemData> items = new ArrayList<ItemData>();

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(ItemData item) {
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        ItemData itemData = items.get(position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, viewGroup, false);

        } else {
            View view = new View(context);
            view = (View) convertView;
        }

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_type = (TextView) convertView.findViewById(R.id.tv_type);
        TextView tv_remaining = (TextView) convertView.findViewById(R.id.tv_remaining);

        tv_name.setText(itemData.name);
        tv_type.setText(itemData.type);
        tv_remaining.setText(itemData.remaining);
        Log.d(TAG, "getView() - [ "+position+" ] "+itemData.name);

        //각 아이템 선택 event
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "식료품 : " + itemData.name+", 보관 방법 : " + itemData.type + ", 남은 유통기한 : " + itemData.remaining, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;  //뷰 객체 반환
    }
}
