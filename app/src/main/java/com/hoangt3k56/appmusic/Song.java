package com.hoangt3k56.appmusic;

import android.net.Uri;

public class Song {
    private String name;
    private Uri uri;

    public Song(String name, Uri uri) {
        this.name = editName(name);
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    private String editName(String name) {
        String []names  = name.split("Download%2F");
        String []res    = names[names.length - 1].split("-");
        return res[0];
    }
}
