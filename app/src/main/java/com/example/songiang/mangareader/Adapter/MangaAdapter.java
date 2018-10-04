package com.example.songiang.mangareader.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.songiang.mangareader.Model.Manga;
import com.example.songiang.mangareader.R;
import com.example.songiang.mangareader.View.DetailMangaActivity;

import java.util.ArrayList;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaHolder> {
    private Activity activity;
    private ArrayList<Manga> listManga;

    public MangaAdapter(Activity activity, ArrayList<Manga> listAticle) {
        this.activity = activity;
        this.listManga = listAticle;
    }

    @Override
    public MangaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_label,parent,false);
        return new MangaHolder(view);
    }

    @Override
    public void onBindViewHolder(final MangaHolder holder, int position) {
        final Manga manga = listManga.get(position);
        holder.chapter.setText(manga.getChapter());
        holder.tvTitle.setText(manga.getTitle());
        Glide.with(activity)
                .load(manga.getThumnail())
                .asBitmap()
                .atMost()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .animate(android.R.anim.fade_in)
                .approximate()
                .into(holder.imgThumnal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity,DetailMangaActivity.class).putExtra("Manga",manga));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listManga.size();
    }

    class MangaHolder extends RecyclerView.ViewHolder{

        private ImageView imgThumnal;
        private TextView tvTitle;
        private TextView chapter;
        public MangaHolder(View itemView) {
            super(itemView);
            imgThumnal = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            chapter = (TextView) itemView.findViewById(R.id.tv_chapter);
        }
    }
}