package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView mEmail,mPassword;
    Button mLog_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLog_in=findViewById(R.id.login_btn2);

        mLog_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        validateInput();

    }
//    private boolean validateInput(){
//        boolean isInputValue = false;
//
//        String email = mEmail.getText().toString().trim();
//        String password = mPassword.getText().toString().trim();
//
//
//            if(!email.isEmpty()){
//                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    if(!password.isEmpty()){
//
//                    }else {
//                        mPassword.setError("password required");
//                        mPassword.requestFocus();
//                    }
//
//                }else {
//                    mEmail.setError("Email Invalid");
//                    mEmail.requestFocus();
//                }
//
//            }else{
//                mEmail.setError(" Email required");
//                mEmail.requestFocus();
//            }
//
//
//
//        return false;
//     }
}
