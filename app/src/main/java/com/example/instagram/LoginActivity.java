package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;


public class LoginActivity extends AppCompatActivity {

    TextView mEmail,mPassword;
    Button mLog_in;
    private FirebaseAuth mAuth;
    ProgressDialog mDialog;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLog_in=findViewById(R.id.login_btn2);
        mAuth = FirebaseAuth.getInstance();

         mDialog = new ProgressDialog(LoginActivity.this);
        
        mLog_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();

                if(!email.isEmpty()){
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(!password.isEmpty()){
                            if(password.length()>=6){
                                doLogin(email,password);
                            }else{
                                Toast.makeText(LoginActivity.this, "Password Must be 6 or more characters", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            mPassword.setError("Password Required");
                            mPassword.requestFocus();
                        }
                    }else{
                        mEmail.setError("Email Invalid");
                        mEmail.requestFocus();
                    }

                }else{
                    mEmail.setError("Email Required");
                    mEmail.requestFocus();
                }

            }
        });

    }

    private void doLogin(final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"Sign In with email and password successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else{
                            Log.w(TAG,"Sign In with email and password Failed",task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failure", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        
    }


}
