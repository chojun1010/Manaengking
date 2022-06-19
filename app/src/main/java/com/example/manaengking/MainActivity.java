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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //--- ListView 설정
        setListView();

    } // 생성자 끝

    private void viewBinding() {
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
    }
    private void setListView() {
        ItemAdapter adapter = new ItemAdapter();
        for(int i=0; i<15; i++) {
            ItemData itemData = new ItemData("우유" + i, "냉장", "14일");
            adapter.addItem(itemData);
        }
        binding.listView.setAdapter(adapter);
    }

}