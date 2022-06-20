package com.example.manaengking;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ShoppingBasketActivity extends AppCompatActivity {

    MainFragment mainFragment;
    EditText inputTobuy;
    Context context;
    public static ShoppingBasketDB shoppingBasketDB = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        mainFragment = new MainFragment();
        //getSupportFragmentManager 을 이용하여 이전에 만들었던 **FrameLayout**에 `fragment_main.xml`이 추가
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();

        Button saveButton = findViewById(R.id.Savebutton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveToBuy();
                Toast.makeText(getApplicationContext(),"추가되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        openDatabase();
    }

    private void saveToBuy(){
        inputTobuy = findViewById(R.id.inputTobuy);
        //EditText에 적힌 글을 가져오기
        String tobuy = inputTobuy.getText().toString();
        //테이블에 값을 추가하는 sql구문 insert...
        String sqlSave = "insert into " + ShoppingBasketDB.TABLE_SHOPPINGBASKET + " (TOBUY) values (" + "'" + tobuy + "')";

        //sql문 실행
        ShoppingBasketDB database = ShoppingBasketDB.getInstance(context);
        database.execSQL(sqlSave);

        //저장과 동시에 EditText 안의 글 초기화
        inputTobuy.setText("");
    }


    public void openDatabase() {
        // open database
        if (shoppingBasketDB != null) {
            shoppingBasketDB.close();
            shoppingBasketDB = null;
        }
        shoppingBasketDB = ShoppingBasketDB.getInstance(this);
        boolean isOpen = shoppingBasketDB.open();
        if (isOpen) {
            Log.d(TAG, "ShoppingBasket database is open.");
        } else {
            Log.d(TAG, "ShoppingBasket database is not open.");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shoppingBasketDB != null) {
            shoppingBasketDB.close();
            shoppingBasketDB = null;
        }
    }
}