package com.example.mycounsille;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView iconSlogan;
    TextView iconShakeHands;
    Animation fromTop, fromLeft, fromRight;
    Button buttonLogin,buttonSwitchRegister;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        iconShakeHands = (TextView)findViewById(R.id.iconShakeHands);
        iconSlogan = (ImageView)findViewById(R.id.iconslogan);
        buttonSwitchRegister=findViewById(R.id.buttonSwitchRegister);
        buttonLogin=findViewById(R.id.buttonLogin);

        fromTop = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.animator.anim_from_top_to_bottom);
        fromLeft = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.animator.anim_from_left_to_right);
        fromRight = AnimationUtils.loadAnimation(SplashScreenActivity.this,R.animator.anim_from_right_to_left);

        iconShakeHands.setAnimation(fromTop);
        // iconLogo.setAnimation(fromLeft);
        iconSlogan.setAnimation(fromRight);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonSwitchRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreenActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set thread set to 3 seconds for the welcome screen

    }

    private String readFile() {
        try {
            //Opens a file reading stream.
            FileInputStream in = this.openFileInput("session.txt");
            if (in == null)
                return "";
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            StringBuilder sb = new StringBuilder();
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }

            return sb.toString();

        } catch (Exception e) {
            return "";
        }
    }
}