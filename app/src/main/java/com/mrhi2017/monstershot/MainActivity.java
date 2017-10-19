package com.mrhi2017.monstershot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        loadData();

        mediaPlayer = MediaPlayer.create(this, R.raw.dragon_flight);
        mediaPlayer.setLooping(true);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaPlayer!=null&&!mediaPlayer.isPlaying()){
            if(G.isMusic)mediaPlayer.setVolume(0.5f, 0.5f);
            else mediaPlayer.setVolume(0,0);
            mediaPlayer.start();
        }

    }

    void loadData() {

        SharedPreferences prefer = getSharedPreferences("data", MODE_PRIVATE);
        G.gem = prefer.getInt("Gem", 0);
        G.champion = prefer.getInt("Champion", 0);
        G.kind = prefer.getInt("Kind", 0);
        G.imgUri = prefer.getString("ImgUri", null);
        G.isMusic = prefer.getBoolean("IsMusic", true);
        G.isSound = prefer.getBoolean("IsSound", true);
        G.isVibrate = prefer.getBoolean("IsVibrate", true);

    }

    public void clickStart(View v) {
        //GameActivity실행
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

        //액티비티전환시 애니메이션효과....
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    public void clickExit(View v) {
        finish();
    }
}
