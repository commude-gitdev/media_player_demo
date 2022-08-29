package com.hoangt3k56.appmusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtFile, txtNameMusic;
    Button btnSubmit;
    ImageView imgNext, imgPlay, imgPrev;
    private int MY_FILE_CODE = 123;
    MediaPlayer mediaPlayer;
    ArrayList<Uri> songArrayList;
    int postion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        songArrayList = new ArrayList<>();
//        songArrayList.add(Uri.parse(name));
//        khoiTaoMediaPlayer();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType(Constant.AUDIO);
                startActivityForResult(intent, MY_FILE_CODE);
            }
        });
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, ""+songArrayList.size(), Toast.LENGTH_SHORT).show();
                if(songArrayList.size()>0){
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        imgPlay.setImageResource(R.drawable.play);
                    }
                    else{
                        mediaPlayer.start();
                        imgPlay.setImageResource(R.drawable.pause);
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Vui long chon file", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songArrayList.size()>0){
                    postion ++;
                    if(postion > songArrayList.size()-1){
                        postion = 0;
                    }
                    mediaPlayer.stop();
                    khoiTaoMediaPlayer();
                    mediaPlayer.start();
                }else{
                    Toast.makeText(MainActivity.this, "Vui long chon file", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(songArrayList.size() > 0){
                    postion --;
                    if(postion < 0){
                        postion = songArrayList.size()-1;
                    }
                    mediaPlayer.stop();
                    khoiTaoMediaPlayer();
                    mediaPlayer.start();
                }else{
                    Toast.makeText(MainActivity.this, "Vui long chon file", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_FILE_CODE && resultCode == MainActivity.RESULT_OK ){
            if ((data != null) && (data.getData() != null)){
                Uri audioFileUri = data.getData();
                Log.d("TAIDEV", "ch = "+audioFileUri);
                songArrayList.add(audioFileUri);
                if(songArrayList.size() == 1){
                    khoiTaoMediaPlayer();
                }
                // Now you can use that Uri to get the file path, or upload it, ...
//                Toast.makeText(this, "hhhhh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void khoiTaoMediaPlayer() {
        Uri uri = songArrayList.get(postion);
        mediaPlayer = MediaPlayer.create(MainActivity.this,uri);
        txtNameMusic.setText("Bai "+ (postion+1));


    }
    private void initUi() {
        txtFile = (TextView) findViewById(R.id.txtFile);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtNameMusic = (TextView) findViewById(R.id.txtNameMusic);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgPrev = (ImageView) findViewById(R.id.imgPrev);
    }

}