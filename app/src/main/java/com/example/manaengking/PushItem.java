//--- PushItem
// 재료 추가

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PushItem extends AppCompatActivity {
    public String name;
    public String type;
    public String datePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_item);
        datePick = "";

        ImageButton imageButton = findViewById(R.id.calendarButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });

        Button button = findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemData(view);
            }
        });
    }
    public void addItemData(View view) {
        TextView tv_name = findViewById(R.id.pi_textView1);


        Intent intent = new Intent(PushItem.this, MainActivity.class);
        intent.putExtra("이름", tv_name.getText());
        intent.putExtra("보관방법", "냉장");
        intent.putExtra("날짜", convertDate(datePick));
    }
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processDatePickerResult(int year, int month, int day){
        datePick = "";
        datePick += Integer.toString(year) + "-";
        datePick += Integer.toString(month+1) + "-";
        datePick += Integer.toString(day);

        Toast.makeText(this,"Date: "+ datePick, Toast.LENGTH_SHORT).show();
    }
    private long convertDate(String datePick) {
        Calendar calToday = Calendar.getInstance();
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dDay = new Date();
        try {
            dDay = df.parse(datePick);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(dDay);

        long lToday = calToday.getTimeInMillis() / (24*60*60*1000);
        long lDDay = c.getTimeInMillis() / (24*60*60*1000);

        long subRet = lDDay - lToday;

        return subRet;
    }
}
