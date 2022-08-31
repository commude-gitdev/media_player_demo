package com.hoangt3k56.appmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SongAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private ArrayList<Song> songArrayList;

    public SongAdapter(MainActivity context, int layout, ArrayList<Song> songArrayList) {
        this.context = context;
        this.layout = layout;
        this.songArrayList = songArrayList;
    }

    private class Holder{
        TextView txtNumberSong, txtNameListSong;
    }

    @Override
    public int getCount() {
        return songArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder   = null;
        Song song       = songArrayList.get(i);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view                          = layoutInflater.inflate(layout, null);

        if (holder == null){
            holder = new Holder();
            holder.txtNumberSong    = (TextView) view.findViewById(R.id.txtNumberSong);
            holder.txtNameListSong  = (TextView) view.findViewById(R.id.txtNameListSong);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.txtNumberSong.setText(""+(1 + i));
        holder.txtNameListSong.setText(song.getName());

        holder.txtNameListSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.clickNextSongList(i);
            }
        });

        return view;
    }
}
