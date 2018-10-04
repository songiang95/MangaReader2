package com.example.songiang.mangareader.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.songiang.mangareader.Model.Chapter;
import com.example.songiang.mangareader.R;

public class ReadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        Intent intent = getIntent();
        Chapter chapter = (Chapter)intent.getSerializableExtra("Chapter");
        String url = chapter.getUrl();
        TextView tView = (TextView) findViewById(R.id.chapter_url);
        tView.setText(url);
    }

}
