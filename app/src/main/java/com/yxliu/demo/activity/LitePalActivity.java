package com.yxliu.demo.activity;

import android.os.Bundle;
import android.util.Log;

import com.yxliu.demo.R;
import com.yxliu.demo.activity.base.BaseActivity;
import com.yxliu.demo.model.Book;

import org.litepal.LitePal;

import java.util.List;

public class LitePalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);

        init();
    }

    private void init() {

        findViewById(R.id.btn_pal_create_database).setOnClickListener(v -> LitePal.getDatabase());

        findViewById(R.id.btn_pal_add_data).setOnClickListener(v -> {
            Book book = new Book();
            book.setName("Sense and Sensibility");
            book.setAuthor("Jane Austen");
            book.setPages(448);
            book.setPrice(52.99);
            book.setPress("Unknow");
            book.save();

            Book book2 = new Book();
            book2.setName("First Love");
            book2.setAuthor("屠格涅夫");
            book2.setPages(448);
            book2.setPrice(52.99);
            book2.setPress("Unknow");
            book2.save();
        });

        findViewById(R.id.btn_pal_update_data).setOnClickListener(v -> {
            Book book = new Book();
            book.setPress("Anchor");
            book.setPrice(9.99);
            book.updateAll("name = ? and author = ?", "Sense and Sensibility", "Jane Austen");
        });

        findViewById(R.id.btn_pal_delete_data).setOnClickListener(v -> LitePal.deleteAll(Book.class, "price > ?", "10"));

        findViewById(R.id.btn_pal_query_data).setOnClickListener(v -> {
//            其他查询语句
//            List<Book> bookList = LitePal.select("name","author").find(Book.class);
//            List<Book> bookList = LitePal.where("pages > ?","400").find(Book.class);
//            List<Book> bookList = LitePal.order("price desc").find(Book.class);
//            List<Book> bookList = LitePal.limit(3).find(Book.class);
//            offset(1)查询结果的偏移量为一
//            List<Book> bookList = LitePal.limit(3).offset(1).find(Book.class);
            List<Book> bookList = LitePal.select("name","author","pages")
                    .where("pages > ?","400")
                    .order("pages")
                    .limit(3)
                    .offset(1)
                    .find(Book.class);

//            List<Book> bookList = LitePal.findAll(Book.class);
            for (Book book : bookList) {
                Log.d("LitePalActivity---", "Book name is " + book.getName());
                Log.d("LitePalActivity---", "Book author is " + book.getAuthor());
                Log.d("LitePalActivity---", "Book pages is " + book.getPages());
                Log.d("LitePalActivity---", "Book press is " + book.getPress());
                Log.d("LitePalActivity---", "Book price is " + book.getPrice());
            }
        });
    }


}
