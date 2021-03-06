package com.example.mycounsille;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;

public class SettingsFragment extends Fragment implements View.OnClickListener{
    private Button btnLogout, btnChangePassWord;
    private Button btnPrivatePolicy, btnAboutApp,btncalendar;

    FirebaseAuth firebaseAuth;

    private DatabaseReference nodeMoreInfo, nodeMoreInfo2;

    String status = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        btnLogout = v.findViewById(R.id.btnLogout);
        btnChangePassWord = v.findViewById(R.id.btnAccountPolicy);
        btnLogout.setOnClickListener(this);
        btnChangePassWord.setOnClickListener(this);;

        return v;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogout:
                firebaseAuth.getInstance().signOut();
                saveFile();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case  R.id.btnAccountPolicy:
                Intent intent2 = new Intent(getActivity(), ChangePassWordActivity.class);
                startActivity(intent2);
                break;
        }
    }
    public  void saveFile()
    {
        try {

            // Opens a file recording stream.
            FileOutputStream out = getActivity().openFileOutput("session.txt", Context.MODE_PRIVATE);
            // Record data.
            String fulluser = "";
            out.write(fulluser.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(getActivity(),"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    String changeStatus;

    public void showDialog(int pos){
        final AlertDialog.Builder mBuider = new AlertDialog.Builder(getActivity());
        mBuider.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nodeMoreInfo2 = FirebaseDatabase.getInstance().getReference().child("status")
                        .child(FirebaseAuth.getInstance().getUid());
                nodeMoreInfo2.setValue(changeStatus);
                Toast.makeText(getActivity(),"Successfully updated privacy settings!",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        mBuider.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuider.create();
        mDialog.show();
    }
}
