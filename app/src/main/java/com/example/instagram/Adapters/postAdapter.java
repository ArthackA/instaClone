package com.example.instagram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

public class postAdapter extends RecyclerView.Adapter<postAdapter.PostViewHolder> {
    private Context mContext;


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list,parent,false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {
        return 5;
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        TextView mUsername;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
