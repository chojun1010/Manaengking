//--- CookingRcmd
// 요리 추천

package com.example.manaengking;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CookingRecmd extends AppCompatActivity {
    public static Button button;
    public static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_recmd);
        textView = findViewById(R.id.apiTextView);
        textView.setText("추천할 요리를 불러오는 중입니다.");
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setScrollY(0);
        String[] tmp = MainActivity.datas.split(",");
        if(MainActivity.items == 0) {
            textView.setText("냉장고에 재료가 없어 추천할만한 요리가 없습니다!");
        }
        else {
            String rsrc1 = tmp[0];
            String rsrc2 = "";
            if(MainActivity.items >= 2) rsrc2 = tmp[3];
            callAPI.getJson(rsrc1, rsrc2);
        }

    }

}