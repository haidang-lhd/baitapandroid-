package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Play_Song_Activity extends AppCompatActivity  {
    TextView tenBH,tenCS,time_prossec,time_total;
    ImageView next,play,back;
    SeekBar seekBar;
    String dir;
    ListView list_song;
    ArrayList<String> paths;
    public MediaPlayer mediaPlayer;
    MediaMetadataRetriever mmr;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__song_);
        findView();
        Intent intent = getIntent();
        dir = intent.getStringExtra(MainActivity.NAME);
        initList();
        String[] spaths = new String[paths.size()];
        list_song.setAdapter(new adapter_list_song(this, R.layout.adapter_item_song, paths.toArray(spaths)));
        khoitaoMediaPlayer();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            play.setImageResource(R.drawable.play);
        }
        else {
            mediaPlayer.start();
            play.setImageResource(R.drawable.pause);
        }
        setTimeTotal();
        UpdateTimeSong();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    position--;
                    if(position < 0 )
                        position = paths.size()-1;
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    khoitaoMediaPlayer();
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                    setTimeTotal();
                    UpdateTimeSong();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    position++;
                    if(position > paths.size()-1)
                        position = 0;
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.stop();
                    khoitaoMediaPlayer();
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                    setTimeTotal();
                    UpdateTimeSong();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   if(mediaPlayer.isPlaying()){
                       mediaPlayer.pause();
                       play.setImageResource(R.drawable.play);
                   }
                   else {
                       mediaPlayer.start();
                       play.setImageResource(R.drawable.pause);
                   }
                   setTimeTotal();
                   UpdateTimeSong();
           }
       });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

    }


    public void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                time_prossec.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > paths.size()-1)
                            position = 0;
                        if(mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        khoitaoMediaPlayer();
                        mediaPlayer.start();
                        play.setImageResource(R.drawable.pause);
                        setTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }

    public void setTimeTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        time_total.setText(dinhDangGio.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }

    public void khoitaoMediaPlayer(){
            mmr = new MediaMetadataRetriever();
            mmr.setDataSource(paths.get(position));
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            tenCS.setText(artist);
            tenBH.setText(title);
            String path = paths.get(position);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void initList() {
        paths = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+dir;
        File file = new File(path);
        File files[] = file.listFiles();
        int lenght = files.length;
        if(lenght>0) {
            for (int i = 0; i < lenght; i++) {
                String s = files[i].getName();
                if (s.endsWith(".mp3")) {
                    paths.add(files[i].getAbsolutePath());
                }
            }
        }
    }

    private void findView() {
        tenBH = findViewById(R.id.tenBH);
        tenCS = findViewById(R.id.tenCS);
        time_prossec = findViewById(R.id.time_prossec);
        time_total = findViewById(R.id.time_total);
        seekBar = findViewById(R.id.seekBar);
        back = findViewById(R.id.btn_back);
        play = findViewById(R.id.btn_play);
        next = findViewById(R.id.btn_next);
        list_song = findViewById(R.id.list_song);
    }
}
