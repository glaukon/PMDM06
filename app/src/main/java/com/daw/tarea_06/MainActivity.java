package com.daw.tarea_06;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    private   Handler mHandler;
    private Runnable mUpdateSeekbar;
    private SeekBar seekBarVolumen;
    private final int respuestaMusica=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        mp = MediaPlayer.create(this, R.raw.terra);
        seekBarVolumen = (SeekBar) findViewById(R.id.seekBarVolumen);
        seekBarVolumen.setProgress(100);
        mp.setVolume(1.0f,1.0f);
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        SeekBar sbDesplazar = (SeekBar) findViewById(R.id.seekBar);
        ImageButton btnAdelante  = (ImageButton) findViewById(R.id.btnAdelante);
        ImageButton btnStop = (ImageButton) findViewById(R.id.btnStop);
        ImageButton btnExplorador = (ImageButton) findViewById(R.id.explorador);


        mHandler = new Handler();
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(mp != null){
                    int currentPosition = mp.getCurrentPosition();
                    int totalDuration = mp.getDuration();
                    sbDesplazar.setProgress(currentPosition * 100 / totalDuration);
                    mHandler.postDelayed(this, 1000);
                }
            }
        };
        // Button btnStop = (Button) findViewById(R.id.btn_Stop);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("andres","play");
                if (mp.isPlaying()) {
                    mp.pause();
                   // btnPlay.setText("Play");
                } else {
                    mp.start();
                    mHandler.postDelayed(mUpdateSeekbar, 1000);
                   // btnPlay.setText("Pause");

                }
            }
        });
        btnAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.getDuration()>mp.getCurrentPosition()+10000) {
                    mp.seekTo(mp.getCurrentPosition() + 10000);
                    sbDesplazar.setProgress((mp.getCurrentPosition() + 10000)*100 / mp.getDuration());
                }
            }
        });
        btnExplorador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("audio/*");
                try {
                    startActivityForResult(fileintent, respuestaMusica);
                } catch (ActivityNotFoundException e) {

                }
            }
        });
        seekBarVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float nuevaPosicion = (float)progress/100;
                    mp.setVolume(nuevaPosicion,nuevaPosicion);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbDesplazar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    int nuevaPosicion = mp.getDuration()*progress/100;
                    mp.seekTo(nuevaPosicion);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        btnStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mp.stop();
//                mp = MediaPlayer.create(MainActivity.this, R.raw.terra);
//            }
//        });

        // mp.start();



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        switch (requestCode) {
            case 1001:
                if (resultCode == RESULT_OK ) {
                    Uri filePath = data.getData();
                    //Log.e("andres",filePath);
                    mp.stop();
                    mp.release();
                    mp = MediaPlayer.create(this, filePath);
                    seekBarVolumen.setProgress(100);
                    mp.setVolume(1.0f,1.0f);

                }
        }
    }
}