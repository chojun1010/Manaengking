//--- MainActivity
// 마내킹 메인

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manaengking.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    // view binding
    private ActivityMainBinding binding;
    public ItemAdapter adapter;
    public static Context mContext;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static String datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ItemAdapter();
        viewBinding(); // view binding
        mContext = this;

        //--- Button Listener 설정
        binding.pushItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PushItem.class);
                startActivity(intent);
            }
        });
        binding.cookingRecmdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CookingRecmd.class);
                startActivity(intent);
            }
        });
        binding.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestart();
            }
        });

        binding.initButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas = "";
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                editor.clear();
                editor.commit();
                refreshScreen();
            }
        });

        //--- ListView 설정
        loadDatas();

    } // 생성자 끝

    private void refreshScreen() {
        finish();//인텐트 종료
        overridePendingTransition(0, 0);//인텐트 효과 없애기
        Intent intent = getIntent(); //인텐트
        startActivity(intent); //액티비티 열기
        overridePendingTransition(0, 0);//인텐트 효과 없애기
    }

    private void loadDatas() {
        String name = "", type = "", strrm = "";
        long remaining = 0;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        datas = pref.getString("데이터", "");
        //System.out.println(datas);
        String[] tmp = datas.split(",");
        for (int i = 0; i < tmp.length; i++) {
            System.out.println("현재 tmp[i] : " + tmp[i]);
            if (i % 3 == 0) {
                name = tmp[i];
            } else if (i % 3 == 1) {
                type = tmp[i];
            } else if (i % 3 == 2) {
                strrm = tmp[i] + "일 남음";
                remaining = Long.parseLong(tmp[i]);
                ItemData itemData = new ItemData(name, type, strrm, remaining);
                adapter.addItem(itemData);
                binding.listView.setAdapter(adapter);
            }
        }
    }
    public void deleteItem(int position) {
        StringBuilder strb = new StringBuilder(datas);
        if(position == 0) {
            int cnt = 0;
            int start = 0;
            while(true) {
                if(cnt == 3) break;
                if(strb.charAt(start) == ',') cnt++;
                strb = strb.deleteCharAt(start);
            }
        }
        else {
            int cnt = 0;
            int comma = 0;
            int start = 0;
            for(int i=0; i<datas.length(); i++) {
                if(datas.charAt(i) == ',') comma++;
                if(comma == 3*position) {
                    start = i+1;
                    break;
                }
            }
            while(true) {
                if(cnt == 3) break;
                if(strb.charAt(start) == ',') cnt++;
                strb = strb.deleteCharAt(start);
            }
        }

        datas = strb.toString();
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.commit();
        editor.putString("데이터", datas);
        editor.apply();
        loadDatas();
        refreshScreen();
    }
    private void viewBinding() {
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
    }
    private void setListView() {
        Intent intent = getIntent();

        if(intent.hasExtra("이름") && intent.hasExtra("보관방법") && intent.hasExtra("날짜")) {
            String name = intent.getStringExtra("이름");
            String type = intent.getStringExtra("보관방법");
            long remaining = intent.getLongExtra("날짜", 0);

            ItemData itemData = new ItemData(name, type, remaining + "일 남음", remaining);
            adapter.addItem(itemData);
            binding.listView.setAdapter(adapter);
        }
    }
}