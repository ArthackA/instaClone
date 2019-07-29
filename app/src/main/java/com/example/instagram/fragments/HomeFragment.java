package com.example.instagram.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagram.Adapters.postAdapter;
import com.example.instagram.models.Posts;
import com.example.instagram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView mRecyclerView;
    postAdapter mPostAdapter;
    List<Posts> mPost;
    FirebaseAuth mAuth;
    DatabaseReference postRef;
    String userId;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = view.findViewById(R.id.home_recycler_View);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLm = new LinearLayoutManager(getContext());
        linearLm.setReverseLayout(true);
        linearLm.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLm);

        readPosts();

        return view;
    }

    private void readPosts() {
        mPost = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        postRef = FirebaseDatabase.getInstance().getReference("Posts");

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPost.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Posts post = snapshot.getValue(Posts.class);
                    mPost.add(post);
                }

                mPostAdapter = new postAdapter(getContext(), mPost);
                mRecyclerView.setAdapter(mPostAdapter);
                mPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
