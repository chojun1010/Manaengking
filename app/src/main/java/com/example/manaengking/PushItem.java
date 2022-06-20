//--- PushItem
// 재료 추가

package com.example.manaengking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
        setSpinner();
    }
    public void setSpinner() {
        String[] types = {"냉장", "냉동", "실온"};
        TextView textView = (TextView) findViewById(R.id.spinner_tv);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                types);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 선택되면
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = types[position];
                textView.setText(types[position]);
            }
            // 아무것도 선택되지 않은 상태일 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("선택: ");
            }
        });

    }


    public void addItemData(View view) {
        EditText editText = findViewById(R.id.editText1);
        name = editText.getText().toString();
        long remaining = convertDate(datePick);

        MainActivity.datas += name + "," + type + "," + remaining + ",";

        MainActivity.pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        MainActivity.editor = MainActivity.pref.edit();
        MainActivity.editor.putString("데이터", MainActivity.datas);
        MainActivity.editor.apply();

        Toast.makeText(this,"추가하였습니다", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PushItem.this, MainActivity.class);
        startActivity(intent);
        finish();
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
