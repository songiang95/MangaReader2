package com.example.songiang.mangareader.Model;

import java.io.Serializable;

public class Manga implements Serializable {
    private String title;
    private String url;
    private String thumnail;
    private String decription;
    private String chapter;
    public Manga() {
    }

    public Manga(String title, String url, String thumnail, String decription, String chapter) {
        this.title = title;
        this.url = url;
        this.thumnail = thumnail;
        this.decription = decription;
        this.chapter = chapter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
    public String getChapter()
    {
        return chapter;
    }
    public void setChapter(String chapter)
    {
        this.chapter= chapter;
    }
}
