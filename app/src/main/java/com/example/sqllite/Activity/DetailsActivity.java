package com.example.sqllite.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sqllite.Model.Grocery;
import com.example.sqllite.R;

import java.util.IdentityHashMap;

public class DetailsActivity extends AppCompatActivity {

    Grocery grocery;
    private TextView ItemName;
    private TextView ItemQty;
    private TextView ItemDate;
    private int Id;
    private ImageButton ItemEdit;
    private ImageButton ItemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ItemName=findViewById(R.id.ItemNameDet);
        ItemQty=findViewById(R.id.ItemQtyDet);
        ItemDate=findViewById(R.id.ItemDateDet);
        ItemEdit=findViewById(R.id.ItemEditDEt);
        ItemDelete=findViewById(R.id.ItemDeleteDet);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            ItemName.setText(bundle.getString("name"));
            ItemQty.setText(bundle.getString("qty"));
            ItemDate.setText(bundle.getString("date"));
            Id=bundle.getInt("id");
        }




    }
}
