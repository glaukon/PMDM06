package com.daw.tarea_06;

import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    private   Handler mHandler;
    private Runnable mUpdateSeekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity1);
        mp = MediaPlayer.create(this, R.raw.terra);
        Button btnvolumen;
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        SeekBar sbDesplazar = (SeekBar) findViewById(R.id.seekBar);
        ImageButton btnAdelante  = (ImageButton) findViewById(R.id.btnAdelante);
        ImageButton btnStop = (ImageButton) findViewById(R.id.btn_Stop);

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

}