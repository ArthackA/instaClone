package com.example.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    ProgressDialog mDialog;
    EditText mUsername, mEmail, mPassword;
    Button mRegister;
    TextView mMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        initializer();

        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mUsername.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("please wait ");
                mDialog.show();

                if (!name.isEmpty()) {
                    if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (!password.isEmpty()) {
                            if (password.length() >= 6) {
                                doRegister(name, email, password);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Minimum password length should be atleast 6 characters", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            mPassword.setError("Password Required");
                            mPassword.requestFocus();
                        }

                    } else {
                        mDialog.dismiss();
                        mEmail.setError("Email Required");
                        mEmail.requestFocus();
                    }
                } else {
                    mDialog.dismiss();
                    mUsername.setError("Username Required");
                    mUsername.requestFocus();
                }
            }
        });

    }

    public void initializer() {

        mRegister = findViewById(R.id.reg_btn);
        mMember = findViewById(R.id.txt_member);
        mUsername = findViewById(R.id.reg_username);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);

    }

    private void doRegister(final String name, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            mReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
                            HashMap<String, Object> mHashmap = new HashMap<>();
                            mHashmap.put("id", userId);
                            mHashmap.put("username", name);
                            mHashmap.put("email", email);
                            mHashmap.put("Bio", "");
                            mHashmap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/instagram-f76f2.appspot.com/o/account.png?alt=media&token=2bea5d03-2f62-402a-95b5-4cf573f19ffb");

                            mReference.setValue(mHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                        finish();
                                        mDialog.dismiss();
                                    }

                                }
                            });


                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }

                    }
                });
    }


}