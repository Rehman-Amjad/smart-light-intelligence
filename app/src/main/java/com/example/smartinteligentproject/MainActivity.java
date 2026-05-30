package com.example.smartinteligentproject;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartinteligentproject.Model.LoginScreen;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        textView=findViewById(R.id.textView);
        img1=findViewById(R.id.img1);


        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.myanimation);
        textView.setAnimation(myanim);
        img1.setAnimation(myanim);



        Thread mythread=new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent=new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        mythread.start();

    }
}