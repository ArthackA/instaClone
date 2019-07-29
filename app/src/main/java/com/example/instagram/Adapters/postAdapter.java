package com.example.instagram.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.models.Posts;
import com.example.instagram.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.PostViewHolder> {

    private Context mContext;
    private List<Posts> mPosts;

    public postAdapter(Context mContext, List<Posts> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_list,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Posts post = mPosts.get(position);
        holder.mDescription.setText(post.getDescription());
        Picasso.get()
                .load(post.getImageUrl())
                .into(holder.mImagePost);
        
    }


    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder{

        TextView mUsername,mPublisher,mDescription;
        ImageView mImagePost;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.usernamePost);
            mDescription = itemView.findViewById(R.id.postDescription);
            mPublisher =itemView.findViewById(R.id.UsernamePoster);
            mImagePost=itemView.findViewById(R.id.PostImage);


        }
    }
}
