package com.yxliu.demo.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;
import com.yxliu.demo.util.MyDatabaseHelper;

public class SQLiteActivity extends BaseActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_lite);

        init();
    }

    @SuppressLint("Recycle")
    private void init() {

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 4);

        findViewById(R.id.btn_create_database).setOnClickListener(v -> dbHelper.getWritableDatabase());

        findViewById(R.id.btn_add_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
//            组装第一条数据
            values.put("name", "First Love2");
            values.put("author", "屠格涅夫");
            values.put("pages", 600);
            values.put("price", 29.9);
            db.insert("Book", null, values);

            values.clear();

//            组装第二条数据
            values.put("name", "Sense and Sensibility");
            values.put("author", "Jane Austen");
            values.put("pages", 448);
            values.put("price", 52.10);
            db.insert("Book", null, values);

            Toast.makeText(this, "Add succeeded", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_update_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("price", 19.99);
            db.update("Book", values, "name = ?", new String[]{"Sense and Sensibility"});

            Toast.makeText(SQLiteActivity.this, "update succeeded", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_delete_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("Book", "pages > ?", new String[]{"500"});

            Toast.makeText(SQLiteActivity.this, "delete succeeded", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.btn_query_data).setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Book", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));

                    Log.d("SQLiteActivity---", "Book name is " + name);
                    Log.d("SQLiteActivity---", "Book author is " + author);
                    Log.d("SQLiteActivity---", "Book pages is " + pages);
                    Log.d("SQLiteActivity---", "Book price is " + price);
                } while (cursor.moveToNext());
            }


            Toast.makeText(SQLiteActivity.this, "Query succeeded", Toast.LENGTH_SHORT).show();

//            db.execSQL("insert into Book (name, author, pages,price) values (?, ?, ?, ?)", new String[]{"Sense and Sensibility", "Jane Austen", "448", "52.10"});
//            db.execSQL("update Book set price = ? where name = ?",new String[]{"19.99","Sense and Sensibility"});
//            db.execSQL("delete from Book where pages > ?",new String[]{"550"});
//            db.rawQuery("select * from Book",null);

            cursor.close();
        });
    }
}
