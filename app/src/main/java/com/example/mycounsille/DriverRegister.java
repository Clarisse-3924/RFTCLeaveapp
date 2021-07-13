package com.example.mycounsille;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
public class DriverRegister extends AppCompatActivity{
    private EditText txtUserName, txtPassWord,dateOfBirth,txtRePassWord,txtFullName,txtAddress,txtPhoneNumber,txtRole;
    private Button btnRegister, btnCancel;
    private RadioGroup radioGroupGender;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private boolean gender=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference();

        btnRegister = findViewById(R.id.buttonRegister);
        txtUserName = findViewById(R.id.editTextUsername);
        txtPassWord = findViewById(R.id.editTextPassword);
        txtRePassWord = findViewById(R.id.editTextRetypePassword);
        txtFullName = findViewById(R.id.editTextFullName);
        txtAddress = findViewById(R.id.editTextAddress);
        txtPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        txtRole = findViewById(R.id.editRole);
        dateOfBirth= findViewById(R.id.editTextDateOfBirth);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = radioGroupGender.getCheckedRadioButtonId();
                View radioButton = radioGroupGender.findViewById(radioButtonID);
                int idx = radioGroupGender.indexOfChild(radioButton);
                RadioButton r = (RadioButton) radioGroupGender.getChildAt(idx);
                String selectedtext = r.getText().toString();
                if (selectedtext.equals("male")) {
                    gender = true;
                } else {
                    gender = false;
                }
            }
        });

        btnCancel = findViewById(R.id.buttonCancelRegister);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(DriverRegister.this, Admin.class);
                startActivity(iLogin);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = txtUserName.getText().toString().trim();
                final String password = txtPassWord.getText().toString().trim();
                final String repassword = txtRePassWord.getText().toString().trim();
                final String fullname = txtFullName.getText().toString().trim();
                final String address = txtAddress.getText().toString().trim();
                final String phonenumber = txtPhoneNumber.getText().toString().trim();
                final String date = dateOfBirth.getText().toString().trim();
                final String role = txtRole.getText().toString().trim();


                if(TextUtils.isEmpty(repassword)){
                    txtRePassWord.setError("Name required");
                    return;
                }
                if(TextUtils.isEmpty(fullname)){
                    txtFullName.setError("Name required");
                    return;
                }
                if(TextUtils.isEmpty(phonenumber)){
                    txtPhoneNumber .setError(" Required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    txtUserName .setError(" Required");
                    return;
                }
                if(phonenumber.length()!=10){
                    txtPhoneNumber.setError("Phone number must be ten");
                    return;
                }
                if(TextUtils.isEmpty(date)){
                    dateOfBirth.setError("Required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter a password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password must be longer than 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(), "password incorrect!", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (!task.isSuccessful()) {
                                    Toast.makeText(DriverRegister.this, "Registration error, please check again. Each email only registered 1 time only!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DriverRegister.this, "Registration is complete!",
                                            Toast.LENGTH_SHORT).show();

                                    Driver account = new Driver(email,phonenumber,address,date,"",fullname,role,gender);
                                    String uid = firebaseAuth.getCurrentUser().getUid();
                                    databaseReference.child("drivers").child(uid).setValue(account);
                                    startActivity(new Intent(DriverRegister.this, Admin.class));
                                    finish();
                                }
                            }

                        });
            }
        });
    }
}