package com.example.sqllite.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.sqllite.Model.Grocery;
import com.example.sqllite.R;
import com.example.sqllite.UI.RecyclerViewAdapter;
import com.example.sqllite.data.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder diallogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText Quantity;
    private Button saveButton;
    public DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> ListItems;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHandler(this);
        //OpenListactivityDirectly();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreatePopupDailog();

            }
        });
        recyclerView=findViewById(R.id.recyclerViewMain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groceryList=new ArrayList<>();
        ListItems=new ArrayList<>();

        groceryList=db.getAllGrocery();
        for(Grocery c:groceryList)
        {
            Grocery grocery =new Grocery();
            grocery.setGroceryItem(c.getGroceryItem());
            grocery.setQuantity("Qty :"+c.getQuantity());
            grocery.setId(c.getId());
            grocery.setDateOfAddedeItem("Added "+c.getDateOfAddedeItem());
            ListItems.add(grocery);
        }
        recyclerViewAdapter=new RecyclerViewAdapter((ArrayList<Grocery>)  ListItems,this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void CreatePopupDailog()
    {
        diallogBuilder=new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup,null);
        groceryItem=view.findViewById(R.id.grocery_Item);
        Quantity=view.findViewById(R.id.groceryQt);
        saveButton=view.findViewById(R.id.saveButton);
        diallogBuilder.setView(view);
        dialog=diallogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!groceryItem.getText().toString().isEmpty() &&
                        !Quantity.getText().toString().isEmpty()) {
                    SaveGroceryToDb(v);
                    Toast.makeText(MainActivity.this, "tabahi", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
    public void SaveGroceryToDb(View v)
    {
        Grocery grocery=new Grocery();
        String newGrocery=groceryItem.getText().toString();
        String newGroceryQuantity=Quantity.getText().toString();
        grocery.setGroceryItem(newGrocery);
        grocery.setQuantity(newGroceryQuantity);

        //add to database
        //db.prob();
        int pos=db.addGrocery(grocery);
        startActivity(new Intent(this,MainActivity.class));
    }

}
