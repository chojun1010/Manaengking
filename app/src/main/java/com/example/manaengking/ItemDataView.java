package com.example.manaengking;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ItemDataView extends LinearLayout {
    TextView tv_name, tv_type, tv_remaining;

    public ItemDataView(Context context) {
        super(context);
        init(context);
    }
    public ItemDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item, this, true);
    }
    public void setName(String name) {
        tv_name.setText(name);
    }
    public void setType(String type) {
        tv_type.setText(type);
    }
    public void setRemaining(String remaining) {
        tv_remaining.setText(remaining);
    }
}
