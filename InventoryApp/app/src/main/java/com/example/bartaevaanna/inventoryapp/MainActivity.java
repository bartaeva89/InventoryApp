package com.example.bartaevaanna.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper=new ProductDbHelper(this);
        insertPet();
        readDatabase();
    }

    private void insertPet() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME, "apple");
        values.put(ProductContract.ProductEntry.COLUMN_PRICE, 2);
        values.put(ProductContract.ProductEntry.COLUMN_QUANTITY, 5);
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME, "Tom");
        values.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER, "+36(20)3333333");
        long newRowId = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, values);
    }

    private void readDatabase(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRICE,
                ProductContract.ProductEntry.COLUMN_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };
        Cursor cursor = db.query(
                ProductContract.ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        try {
            Log.d("result", "The pets table contains " + cursor.getCount() + " pets.\n\n");
            int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneNumberIndex=cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
            Log.e("index", nameColumnIndex+" "+priceColumnIndex+" "+quantityColumnIndex+" "+supplierNameColumnIndex+" "+supplierPhoneNumberIndex);
            while (cursor.moveToNext()) {
                String currentName = cursor.getString(nameColumnIndex);
                int price=cursor.getInt(priceColumnIndex);
                int quantity=cursor.getInt(quantityColumnIndex);
                String supplierName=cursor.getString(supplierNameColumnIndex);
                String supplierPhoneNumber=cursor.getString(supplierPhoneNumberIndex);
                Log.d("result", currentName+" "+price+" "+quantity+" "+supplierName+" "+supplierPhoneNumber);
            }
        } finally {
            cursor.close();
        }
    }
}
