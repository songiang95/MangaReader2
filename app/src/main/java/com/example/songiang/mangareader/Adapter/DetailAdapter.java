package com.example.songiang.mangareader.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.songiang.mangareader.Model.Chapter;
import com.example.songiang.mangareader.R;
import com.example.songiang.mangareader.View.DetailMangaActivity;
import com.example.songiang.mangareader.View.ReadingActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ChapterHolder> {
    private DetailAdapterListener listener;
    private ArrayList<Chapter> listChapter;
    private Activity activity;

    public DetailAdapter (Activity activity, ArrayList<Chapter> listChapter)
    {
        this.activity = activity;
        this.listChapter = listChapter;
    }

    public static class ChapterHolder extends RecyclerView.ViewHolder{
        private TextView tvChapter;
        public ChapterHolder(View itemView)
        {
            super(itemView);
            tvChapter = (TextView) itemView.findViewById(R.id.chapter_number);
        }
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_chapter,viewGroup,false);
        return new ChapterHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailAdapter.ChapterHolder holder, final int position) {
        final Chapter chapter = listChapter.get(position);
        holder.tvChapter.setText(chapter.getName());
        holder.tvChapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (listener!=null)
                {
                    listener.onClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return listChapter.size();
    }

    public static interface DetailAdapterListener{
        public void onClick(int position);
    }

    public void setListener(DetailAdapterListener listener)
    {
        this.listener = listener;
    }
}
