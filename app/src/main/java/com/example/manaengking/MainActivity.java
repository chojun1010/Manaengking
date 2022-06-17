//--- MainActivity

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
//        String[] str = {"가", "나", "다", "라"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str);
//        binding.listView.setAdapter(adapter);

    } // 생성자 끝

    private void viewBinding() {
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
    }

}