package com.hoangt3k56.appmusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtFile, txtNameMusic, txtTimeBegin, txtTimeEnd;
    Button btnSubmit;
    ImageView imgNext, imgPlay, imgPrev;
    MediaPlayer mediaPlayer;
    SeekBar seekbarSong;

    ArrayList<Song> songArrayList;
    ListView listViewSong;
    SongAdapter adapter;
    int postion = 0, beginTime, endTime;
    SimpleDateFormat simpleDateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // select files and transfer data

                Intent intent;
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType(Constant.AUDIO);
                startActivityForResult(intent, Constant.MY_FILE_CODE);
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songArrayList.size() > 0){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        imgPlay.setImageResource(R.drawable.play);
                    } else {
                        mediaPlayer.start();
                        imgPlay.setImageResource(R.drawable.pause);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Vui long chon file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songArrayList.size() > 0){
                    postion++;
                    if (postion > (songArrayList.size() - 1)){
                        postion = 0;
                    }
                    mediaPlayer.stop();
                    khoiTaoMediaPlayer();
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.pause);
                } else {
                    Toast.makeText(MainActivity.this, "Vui long chon file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (songArrayList.size() > 0){
                    postion --;
                    if (postion < 0){
                        postion = songArrayList.size()-1;
                    }
                    mediaPlayer.stop();
                    khoiTaoMediaPlayer();
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.pause);
                } else {
                    Toast.makeText(MainActivity.this, "Vui long chon file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seekbarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtTimeBegin.setText(simpleDateFormat.format(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekbarSong.getProgress());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        if ((requestCode == Constant.MY_FILE_CODE) && (resultCode == MainActivity.RESULT_OK)){
            if (data != null){
                if (data.getClipData() == null){
                    uri = data.getData();
                    Log.d("TAIDEV", "ch = "+uri);
                    songArrayList.add(new Song(uri.toString(), uri));
                    if (songArrayList.size() == 1){
                        khoiTaoMediaPlayer();
                    }
                } else {
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        uri = data.getClipData().getItemAt(i).getUri();
                        songArrayList.add(new Song(uri.toString(), uri));
                        if (songArrayList.size() == 1){
                            khoiTaoMediaPlayer();
                        }
                    }
                }
            }
        }
    }

    private void updateSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtTimeBegin.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                seekbarSong.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 1000);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                postion ++;
                if (postion > (songArrayList.size() - 1)){
                    postion = 0;
                }
                mediaPlayer.stop();
                khoiTaoMediaPlayer();
//                imgPlay.setImageResource(R.drawable.play);
//                mediaPlayer.start();
                imgPlay.setImageResource(R.drawable.play);
            }
        });
    }

    private void editTextSeebar(){
        txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekbarSong.setMax(mediaPlayer.getDuration());
    }

    private void khoiTaoMediaPlayer() {
        Song song = songArrayList.get(postion);
        mediaPlayer = MediaPlayer.create(MainActivity.this,song.getUri());
        txtNameMusic.setText(song.getName());
        editTextSeebar();
        updateSong();
    }

    private void initUi() {
        txtFile         = (TextView) findViewById(R.id.txtFile);
        txtTimeBegin    = (TextView) findViewById(R.id.txtTimeBegin);
        txtTimeEnd      = (TextView) findViewById(R.id.txtTimeEnd);
        txtNameMusic    = (TextView) findViewById(R.id.txtNameMusic);
        btnSubmit       = (Button) findViewById(R.id.btnSubmit);
        imgNext         = (ImageView) findViewById(R.id.imgNext);
        imgPlay         = (ImageView) findViewById(R.id.imgPlay);
        imgPrev         = (ImageView) findViewById(R.id.imgPrev);
        seekbarSong     = (SeekBar) findViewById(R.id.seekbarSong);
        listViewSong    = (ListView) findViewById(R.id.listViewSong);

        songArrayList       = new ArrayList<>();
        simpleDateFormat    = new SimpleDateFormat(Constant.TIME_SONG);
        adapter             = new SongAdapter(this, R.layout.raw_song, songArrayList);
        listViewSong.setAdapter(adapter);
    }

    public void clickNextSongList(int i){
        postion = i;
        mediaPlayer.stop();
        khoiTaoMediaPlayer();
        mediaPlayer.start();
        imgPlay.setImageResource(R.drawable.pause);
    }
}