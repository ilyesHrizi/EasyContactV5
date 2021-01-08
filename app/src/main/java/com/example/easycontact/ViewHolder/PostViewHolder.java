package com.example.easycontact.ViewHolder;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycontact.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    EditText TitelPost , DescriptionPost, imagePost;
    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

    }
}
