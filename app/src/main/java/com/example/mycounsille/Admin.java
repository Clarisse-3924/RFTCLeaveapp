package com.example.mycounsille;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    private DatabaseReference payRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    FirebaseAuth firebaseAuth;
    List<Constants> UsersList;

    private CardView mdoc,mseedoc,mreport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mdoc=findViewById(R.id.btnadddoctor);
        mseedoc=findViewById(R.id.btnseedoctors);
        mreport=findViewById(R.id.btnreport);

        mAuth = FirebaseAuth.getInstance();
        //userRef = FirebaseDatabase.getInstance().getReference().child("doctors");
        //payRef = FirebaseDatabase.getInstance().getReference().child("doctors");

        UsersList = new ArrayList<>();

        mdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this, DriverRegister.class);
                startActivity(intent);
            }
        });

        mseedoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Admin.this,ShowDoctors.class);
                startActivity(intent);
            }
        });

//        mreport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Admin.this, ReportActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        firebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Admin.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}