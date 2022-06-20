//--- MainActivity
// 마내킹 메인

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.manaengking.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    // view binding
    private ActivityMainBinding binding;
    public static ItemAdapter adapter;
    public static Context mContext;
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static String datas;
    public static int items;
    public static String apiResult;

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
                finish();
            }
        });
        binding.cookingRecmdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CookingRecmd.class);
                startActivity(intent);
            }
        });
        binding.initButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        binding.shoppingBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingBasketActivity.class);
                startActivity(intent);
            }
        });

        //--- ListView 설정
        itemSort();
        loadDatas();
        items = adapter.getCount();
        System.out.println(items);

    } // 생성자 끝

    private void init() {
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(mContext);
        // alert의 title과 Messege 세팅
        myAlertBuilder.setTitle("알림");
        myAlertBuilder.setMessage("냉장고를 비우시겠습니까?");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                // OK 버튼을 눌렸을 경우
                datas = "";
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                editor.clear();
                editor.commit();
                refreshScreen();
                Toast.makeText(mContext,"냉장고를 비웠습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancle 버튼을 눌렸을 경우
                Toast.makeText(mContext,"취소하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
        myAlertBuilder.show();
    }

    private void refreshScreen() {
        finish();//인텐트 종료
        overridePendingTransition(0, 0);//인텐트 효과 없애기
        Intent intent = getIntent(); //인텐트
        startActivity(intent); //액티비티 열기
        overridePendingTransition(0, 0);//인텐트 효과 없애기
    }

    private void loadDatas() {
        System.out.println("LoadDatas");
        String name = "", type = "", strrm = "";
        long remaining = 0;
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        datas = pref.getString("데이터", "");

        System.out.println(datas);
        String[] tmp = datas.split(",");
        for (int i = 0; i < tmp.length; i++) {
            System.out.println("현재 tmp[i] : " + tmp[i]);
            if (i % 3 == 0) {
                name = tmp[i];
            } else if (i % 3 == 1) {
                type = tmp[i];
            } else if (i % 3 == 2) {
                strrm = tmp[i];
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

    private void itemSort() {
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        datas = pref.getString("데이터", "");
        System.out.println("Sort");
        String[] tmp = datas.split(",");
        int cnt = tmp.length / 3;
        if(cnt <= 1) return;
        for(int i=2; i<=tmp.length - 3; i+=3) {
            for(int j=i+3; j<=tmp.length; j+=3) {
                if(Long.parseLong(tmp[i]) > Long.parseLong(tmp[j])) {
                    System.out.println((i-2) + ", " + (j-2));
                    itemSwap(tmp, i-2, j-2);
                }
            }
        }
        datas = "";
        for(int i=0; i<tmp.length; i++) {
            datas += tmp[i] + ',';
        }
        System.out.println(datas);

        editor.clear();
        editor.commit();
        editor.putString("데이터", datas);
        tmp = datas.split(",");
        for(int i=0; i<tmp.length; i++) {
            System.out.println("tmp[" + i + "] = " + tmp[i]);
        }
        editor.apply();
    }
    private static void itemSwap(String[] arr, int start1, int start2) {
        String s1 = arr[start1];
        String s2 = arr[start1+1];
        String s3 = arr[start1+2];

        arr[start1] = arr[start2];
        arr[start1+1] = arr[start2+1];
        arr[start1+2] = arr[start2+2];

        arr[start2] = s1;
        arr[start2+1] = s2;
        arr[start2+2] = s3;

    }
}