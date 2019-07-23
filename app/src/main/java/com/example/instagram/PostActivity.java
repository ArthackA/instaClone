package com.example.instagram;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {

     Button mButton;
     EditText mDescription;
     ImageView mImage;
     ProgressDialog mDialog;
     FirebaseUser mUser;
     DatabaseReference mRef;
    private static int PICTURE_RESULT = 100;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initializer();

//        String description= mText.getText().toString();
//        if(description.isEmpty()){
//            mCreatePost.setEnabled(false);
//        }else{
////            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show();
//        }

        mImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = mDescription.getText().toString();
                if(!description.isEmpty()){
                    doPost(description,url);
                }else{
                    mDescription.setError("Description Required");
                    mDescription.requestFocus();
                }

            }
        });


    }

    private void doPost(String description, String url) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        String userID= mUser.getUid();

        if(mUser!=null){
            mRef = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

            HashMap<String, String> hashMap= new HashMap<>();
            hashMap.put("UserId",userID);
            hashMap.put("Description",description);
            hashMap.put("ImageUrl",url);

            mRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PostActivity.this,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Check Your internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else{
            mDialog.dismiss();
            Toast.makeText(this, "Failed to create post", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this,HomeActivity.class));
        }


    }

    private void initializer() {
        mImage.findViewById(R.id.postAct_image);
        mDescription.findViewById(R.id.postDescription_txt);
        mButton.findViewById(R.id.createPost_btn);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICTURE_RESULT && requestCode==RESULT_OK && data!=null){
            Uri uri = data.getData();

            Glide.with(this)
                    .load(uri)
                    .into(mImage);
            assert uri != null;
            final StorageReference reference = FirebaseStorage.getInstance().getReference().child("Post").child(Objects.requireNonNull(uri.getLastPathSegment()));

            reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    url = uri.toString();
                                    showImage(url);
                                    Toast.makeText(PostActivity.this, "success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else{
                        mDialog.dismiss();
                        Toast.makeText(PostActivity.this, "Error Uploading", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

    private void showImage(String url) {
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
        startActivityForResult(Intent.createChooser(intent,"insert Picture"),PICTURE_RESULT);

    }


}
