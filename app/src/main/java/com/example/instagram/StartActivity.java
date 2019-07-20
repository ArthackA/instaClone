package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity{

    Button mLogin,mRegister;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mLogin=findViewById(R.id.login_btn);
        mRegister=findViewById(R.id.Register_btn);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent intent = new Intent(new Intent(StartActivity.this,LoginActivity.class));
                startActivity(intent);
                finish();

            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(StartActivity.this,RegisterActivity.class));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser= FirebaseAuth.getInstance().getCurrentUser();
        if(mUser != null){
            startActivity(new Intent(StartActivity.this,HomeActivity.class));
            finish();
        }
    }

}
