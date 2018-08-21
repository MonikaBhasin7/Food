package com.techsdm.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.techsdm.food.Model.FoodItem;
import com.techsdm.food.Model.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika Bhasin on 07-08-2018.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DatabaseName="food_cart.db";
    public static final String TableName = "food_details";
    private static final int DB_VER=1;
    public static final String ProductName = "ProductName";
    public static final String Quantity = "Quantity";
    public static final String Price= "Price";
    public static final String Discount = "Discount";

    public Database(Context context) {
        super(context,DatabaseName,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TableName +" (ProductName TEXT,Quantity TEXT,Price TEXT,Discount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TableName);
        onCreate(db);
    }

    public boolean addToCart(OrderItem orderItem)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductName,orderItem.getProductName());
        contentValues.put(Quantity,orderItem.getQuantity());
        contentValues.put(Price,orderItem.getPrice());
        contentValues.put(Discount,orderItem.getDiscount());
        long result = db.insert(TableName,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public List<OrderItem> getCarts()
    {
        List<OrderItem> result;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TableName,null);
        result = new ArrayList<>();


        if(res.moveToFirst())
        {
            do {
                String a=res.getString(res.getColumnIndex(ProductName));
                String b=res.getString(res.getColumnIndex(Quantity));
                String c=res.getString(res.getColumnIndex(Price));
                String d=res.getString(res.getColumnIndex(Discount));

                OrderItem orderItem=new OrderItem(a,b,c,d);
                result.add(orderItem);

            }while (res.moveToNext());

        }
        return result;
    }
}
