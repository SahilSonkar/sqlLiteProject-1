package com.example.sqllite.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqllite.Activity.DetailsActivity;
import com.example.sqllite.Model.Grocery;
import com.example.sqllite.R;
import com.example.sqllite.data.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Grocery> ListOfGrocery=new ArrayList<>();
    private Context context;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dailog;
    private LayoutInflater inflater;
    private RecyclerViewAdapter recyclerViewAdapter ;


    public RecyclerViewAdapter(ArrayList<Grocery> listOfGrocery, Context context) {
        ListOfGrocery = listOfGrocery;
        this.context = context;

    }



    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Grocery grocery=ListOfGrocery.get(position);
        holder.groceryItem.setText(grocery.getGroceryItem());
        holder.Quantity.setText(grocery.getQuantity());
        holder.dateOfItem.setText(grocery.getDateOfAddedeItem());

    }

    @Override
    public int getItemCount() {

        return ListOfGrocery.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView groceryItem;
        public TextView Quantity;
        public TextView dateOfItem;
        public Button EditButton;
        public Button DeleteButton;
        public int Id;

        public ViewHolder(@NonNull View itemView, Context ctx) {

            super(itemView);
            context = ctx;
            groceryItem = itemView.findViewById(R.id.List_row_grocery_item);
            Quantity = itemView.findViewById(R.id.List_row_quantity);
            dateOfItem = itemView.findViewById(R.id.List_row_date);
            EditButton = itemView.findViewById(R.id.List_row_EditButton);
            DeleteButton = itemView.findViewById(R.id.List_row_DeleteButton);

            EditButton.setOnClickListener(this);
            DeleteButton.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to next screen
                    Grocery grocery = new Grocery();
                    Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    int pos = getAdapterPosition();
                    grocery = ListOfGrocery.get(pos);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("name", grocery.getGroceryItem());
                    intent.putExtra("qty", grocery.getQuantity());
                    intent.putExtra("id", grocery.getId());
                    intent.putExtra("date", grocery.getDateOfAddedeItem());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.List_row_EditButton: {
                    int pos=getAdapterPosition();
                    Grocery grocery=ListOfGrocery.get(pos);
                    EditItem(grocery);
                    break;
                }
                case R.id.List_row_DeleteButton: {
                    int pos = getAdapterPosition();
                    Grocery grocery = ListOfGrocery.get(pos);
                    DeleteItem(grocery.getId());
                    break;
                }
            }

        }

        public void EditItem(final Grocery grocery)
        {
            alertDialogBuilder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            final View view=inflater.inflate(R.layout.edititem,null);

            TextView title=view.findViewById(R.id.tile);
            title.setText("Edit Grocert Item");
            final EditText itemName=view.findViewById(R.id.Editgrocery);
            final EditText QtyName=view.findViewById(R.id.EditgroceryQt);
            Button saveRecycler=view.findViewById(R.id.Editsavebutton);

            alertDialogBuilder.setView(view);
            dailog=alertDialogBuilder.create();
            dailog.show();

            saveRecycler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseHandler db=new DatabaseHandler(context);
                        grocery.setGroceryItem(itemName.getText().toString());
                        grocery.setQuantity("Qty :"+QtyName.getText().toString());
                        if (!itemName.getText().toString().isEmpty()
                                && !QtyName.getText().toString().isEmpty()) {
                            int x=db.UpdateGrocery(grocery);
                            notifyItemChanged(getAdapterPosition(),grocery);
                            Toast.makeText(context, String.valueOf(x),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                        dailog.dismiss();

                    }
                });
         }

    public void DeleteItem(final int Id)
    {
        alertDialogBuilder=new AlertDialog.Builder(context);
        inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.confirmationaldailog,null);
        Button deletebutton=view.findViewById(R.id.warningDelete);
        Button cancelButton=view.findViewById(R.id.warningCacel);
        alertDialogBuilder.setView(view);
        dailog=alertDialogBuilder.create();
        dailog.show();

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db =new DatabaseHandler(context);
                db.deleteGrocery(Id);
                ListOfGrocery.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                dailog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dailog.dismiss();
            }
        });

    }
    }

}
