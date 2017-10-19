package com.mrhi2017.monstershot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;

public class GameoverActivity extends AppCompatActivity {

    ImageView img_Champion;
    TextView tv_champion;
    TextView tv_score;
    boolean isChampion=false; //챔피언인가?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_champion=(TextView)findViewById(R.id.tv_champion);
        tv_score=(TextView)findViewById(R.id.tv_score);
        img_Champion=(ImageView)findViewById(R.id.img_champion);

        Intent intent=getIntent();
        Bundle data=intent.getBundleExtra("Data");

        int score=data.getInt("Score",0);
        int coin=data.getInt("Coin",0);

        int yourScore=score+coin*10;
        tv_score.setText(String.format("%07d",yourScore));

        if(yourScore>G.champion){
            G.champion=yourScore;
            isChampion=true;
        }

        tv_champion.setText(String.format("%07d",G.champion));


        //챔피언 이미지 표시..
        if(G.imgUri!=null){
            Uri uri=Uri.parse(G.imgUri);
            img_Champion.setImageURI(uri);
        }


    }//Constructor

    void saveData(){
        //data.xml이라는 문서에 저장~~
        SharedPreferences prefer=getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefer.edit();
        editor.putInt("Gem",G.gem);
        editor.putInt("Champion",G.champion);
        editor.putInt("Kind",G.kind);
        editor.putString("ImgUri",G.imgUri);
        editor.putBoolean("IsMusic",G.isMusic);
        editor.putBoolean("IsSound",G.isSound);
        editor.putBoolean("IsVibrate",G.isVibrate);
        editor.commit();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveData();


    }

    public void clickChampion(View v){

        if(!isChampion) return;
        else {

            //내 스마트폰에있는 사진 선택

            if(Build.VERSION.SDK_INT<19){  //API19 미만..
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);


            }else{ //API 19이상..
                Intent intent= new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,10);

            }

        }
    }

    //startActivityForResult()메소드의 호출로 실행되었던 화면이
    //종료되어 이 액티비티가 다시 보이면....자동으로 실행되는 메소드~!


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode== Activity.RESULT_OK){
                    Uri uri=data.getData();
                    img_Champion.setImageURI(uri);

                    G.imgUri=uri.toString();
                }
                break;
        }
    }

    public void clickRetry(View v){
        Intent intent=new Intent(this,GameActivity.class);
        startActivity(intent);
        finish();
    }
    public void clickExit(View v){
        finish();
    }
}
