//--- MainActivity
// 마내킹 메인

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ItemAdapter();
        viewBinding(); // view binding
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
                setListView();
            }
        });

        //--- ListView 설정
        setListView();

    } // 생성자 끝

    private void viewBinding() {
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
    }
    private void setListView() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("이름");
        String type = intent.getStringExtra("보관방법");
        long remaining = intent.getLongExtra("날짜", 0);

        ItemData itemData = new ItemData(name, type, remaining+"일 남음");
        if(!name.equals("") && !type.equals("") && remaining != 0)
        adapter.addItem(itemData);
        binding.listView.setAdapter(adapter);
    }

}