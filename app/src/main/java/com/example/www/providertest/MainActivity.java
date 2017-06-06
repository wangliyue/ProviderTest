package com.example.www.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String newId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addBtn = (Button) findViewById(R.id.add_data);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("author","zhang san");
                values.put("price",15.22);
                values.put("pages",456);
                values.put("name","The book");
                Uri uri = Uri.parse("content://com.example.www.databasetest.provider/Book");
                Uri returnUri = getContentResolver().insert(uri,values);
                newId = returnUri.getPathSegments().get(1);
                Log.d(TAG, "add newId:"+newId);
            }
        });

        Button updateBtn = (Button)findViewById(R.id.update_data);
        updateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("name","The new Book");
                Uri uri = Uri.parse("content://com.example.www.databasetest.provider/Book/"+newId);
                long rows = getContentResolver().update(uri,values,null,null);
                Log.d(TAG, "update rows:"+rows);
            }
        });

        Button deleteBtn = (Button)findViewById(R.id.delete_data);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.www.databasetest.provider/Book/"+newId);
                long rows = getContentResolver().delete(uri,null,null);
                Log.d(TAG, "delete rows:"+rows);
            }
        });

        Button queryBtn = (Button)findViewById(R.id.query_data);
        queryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.www.databasetest.provider/Book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if(cursor != null){
                    while (cursor.moveToNext()){
                        String author = cursor.getString(cursor.getColumnIndex("name"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Log.d(TAG, "author:"+author);
                        Log.d(TAG, "price:"+price);
                        Log.d(TAG, "pages:"+pages);
                        Log.d(TAG, "name:"+name);
                    }
                }
            }
        });
    }
}
