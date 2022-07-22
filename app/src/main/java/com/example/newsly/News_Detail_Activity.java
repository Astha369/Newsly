package com.example.newsly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class News_Detail_Activity extends AppCompatActivity {

    String title, desc, content, imageURL, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");
    }
}