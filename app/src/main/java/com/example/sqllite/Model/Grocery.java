package com.example.sqllite.Model;

public class Grocery {
    private String groceryItem;
    private String quantity;
    private String dateOfAddedeItem;
    private  int Id;

    public Grocery(String groceryItem, String quantity, String dateOfAddedeItem, int id) {
        this.groceryItem = groceryItem;
        this.quantity = quantity;
        this.dateOfAddedeItem = dateOfAddedeItem;
        Id = id;
    }

    public String getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(String groceryItem) {
        this.groceryItem = groceryItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateOfAddedeItem() {
        return dateOfAddedeItem;
    }

    public void setDateOfAddedeItem(String dateOfAddedeItem) {
        this.dateOfAddedeItem = dateOfAddedeItem;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Grocery() {
    }
}
