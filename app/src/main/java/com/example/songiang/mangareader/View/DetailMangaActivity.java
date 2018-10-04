package com.example.songiang.mangareader.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.songiang.mangareader.Adapter.DetailAdapter;
import com.example.songiang.mangareader.Adapter.MangaAdapter;
import com.example.songiang.mangareader.Model.Chapter;
import com.example.songiang.mangareader.Model.Manga;
import com.example.songiang.mangareader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DetailMangaActivity extends AppCompatActivity {

    private DetailAdapter detailAdapter;
    private RecyclerView recyclerView;
    private Intent intent;
    private Manga manga;
    private ArrayList<Chapter> listChapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_manga);
        intent = getIntent();
        manga = (Manga)intent.getSerializableExtra("Manga");
        String url = manga.getUrl();
        recyclerView = (RecyclerView) findViewById(R.id.chapter_category);
        recyclerView.setNestedScrollingEnabled(false);
        setMangaInfor();
        configRecyclerView();
        new LoadChapterTask().execute(url);


    }

    private void setMangaInfor()
    {

        ImageView img = (ImageView) findViewById(R.id.chapter_img);
        Glide.with(this)
                .load(manga.getThumnail())
                .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .animate(android.R.anim.fade_in)
                .approximate()
                .into(img);
        TextView manga_name =(TextView) findViewById(R.id.manga_name);
        manga_name.setText(manga.getTitle());
        TextView manga_description = (TextView) findViewById(R.id.description);
        manga_description.setText(manga.getDecription());
    }
    private void configRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,5);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
    }

    private class LoadChapterTask extends AsyncTask<String, Void, ArrayList<Chapter>>
    {
        @Override
        protected ArrayList<Chapter> doInBackground(String... url)
        {
            Document document = null;
            listChapter = new ArrayList<>();
            try{
                document = (Document) Jsoup.connect(url[0]).get();
                if (document!=null)
                {
                    Elements elements = document.select("div.chapter");
                    for(Element element : elements)
                    {
                        Chapter chapter = new Chapter();
                        Element subject = element.getElementsByTag("a").first();
                        if (subject!=null)
                        {
                            String name = subject.text();
                            if (name.startsWith("Chapter"))
                            {
                                name = name.substring(8);
                            }
                            String chapter_url = subject.attr("href");
                            chapter.setName(name);
                            chapter.setUrl(chapter_url);
                        }
                        listChapter.add(chapter);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return listChapter;
        }
        @Override
        public void onPostExecute(ArrayList<Chapter> chapters)
        {
            super.onPostExecute(chapters);
            detailAdapter = new DetailAdapter(DetailMangaActivity.this,chapters);
            recyclerView.setAdapter(detailAdapter);
            detailAdapter.setListener(new DetailAdapter.DetailAdapterListener(){
                public void onClick(int position)
                {
                    Intent intent = new Intent(DetailMangaActivity.this, ReadingActivity.class);
                    intent.putExtra("Chapter", listChapter.get(position));
                    startActivity(intent);
                }
            });
        }
    }

}
