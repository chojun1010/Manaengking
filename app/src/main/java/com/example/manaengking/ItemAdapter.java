package com.example.manaengking;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
    public ArrayList<ItemData> items = new ArrayList<ItemData>();

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
        if(Long.parseLong(itemData.remaining) <= 3) tv_remaining.setTextColor(Color.parseColor("#D50000"));
        else tv_remaining.setTextColor(Color.parseColor("#000000"));
        tv_remaining.setText(itemData.remaining + "일 남음");
        Log.d(TAG, "getView() - [ "+position+" ] "+itemData.name);

        //각 아이템 선택 event
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(context);
                // alert의 title과 Messege 세팅
                myAlertBuilder.setTitle("알림");
                myAlertBuilder.setMessage(itemData.name + "을(를) 삭제하시겠습니까?");
                // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
                myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        // OK 버튼을 눌렸을 경우
                        ((MainActivity)MainActivity.mContext).deleteItem(position);
                        Toast.makeText(context,"삭제하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancle 버튼을 눌렸을 경우
                        Toast.makeText(context,"취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
                myAlertBuilder.show();
                return true;
            }
        });

        return convertView;  //뷰 객체 반환
    }
}
