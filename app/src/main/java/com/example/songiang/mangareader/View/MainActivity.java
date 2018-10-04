package com.example.songiang.mangareader.View;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.songiang.mangareader.Model.Manga;
import com.example.songiang.mangareader.Adapter.MangaAdapter;
import com.example.songiang.mangareader.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String URL_NEW_MANGA = "http://www.nettruyen.com/tim-truyen";

    private RecyclerView recycler;
    private MangaAdapter mangaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = (RecyclerView) findViewById(R.id.recycler_category);
        configRecyclerView();
        new DownloadTask().execute(URL_NEW_MANGA);
    }

    private void configRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        recycler.hasFixedSize();
        recycler.setLayoutManager(layoutManager);
    }

    //Download HTML bang asystask
     private class DownloadTask extends AsyncTask<String, Void, ArrayList<Manga>>{
        @Override
        protected ArrayList<Manga> doInBackground(String... strings)
        {
            Document document=null;
            ArrayList<Manga> listManga = new ArrayList<>();
            try{
                document = (Document) Jsoup.connect(strings[0]).get();
                if(document!=null)
                {
                    Elements sub = document.select("div.item");
                    for (Element element : sub)
                    {
                        Manga manga = new Manga();
                        Element titleSubject = element.getElementsByClass("title").first();
                        Element imgSubject = element.getElementsByTag("img").first();
                        Element linkSubject = element.getElementsByTag("a").first();
                        Element description = element.getElementsByClass("box_text").first();
                        Element chapterSubject = element.getElementsByAttributeValueContaining("title","Chapter").first();
                    //pasre to model
                        if (titleSubject != null) {
                            String title = titleSubject.text();
                            manga.setTitle(title);
                        }
                        if (imgSubject != null) {
                            String src = imgSubject.attr("data-original");
                            if(src.startsWith("//")){
                                src = src.substring(2);
                                src = "http://"+src;
                            }
                            manga.setThumnail(src);
                        }
                        if (linkSubject != null) {
                            String link = linkSubject.attr("href");
                            manga.setUrl(link);
                        }
                        if (description != null) {
                            String des = description.text();
                            manga.setDecription(des);
                        }
                        if (chapterSubject!=null)
                        {
                            String chapter = chapterSubject.text();
                            manga.setChapter(chapter);
                        }
                        listManga.add(manga);
                    }
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            return listManga;
        }
        @Override
        public void onPostExecute(ArrayList<Manga> mangas)
        {
            super.onPostExecute(mangas);
            mangaAdapter = new MangaAdapter(MainActivity.this,mangas);
            recycler.setAdapter(mangaAdapter);
        }
    }
}
