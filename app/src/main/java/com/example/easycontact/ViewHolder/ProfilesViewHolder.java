package com.example.easycontact.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycontact.R;

public class ProfilesViewHolder extends RecyclerView.ViewHolder  {
    public TextView  NameUser,lastNamesUser , PostContent   ;
    public ImageView ImgProfile;
    public ProfilesViewHolder(@NonNull View itemView) {

        super(itemView);
        NameUser = itemView.findViewById(R.id.NameProfileId);
        lastNamesUser = itemView.findViewById(R.id.LNameProfileId);
        PostContent = itemView.findViewById(R.id.PostId);
        ImgProfile = itemView.findViewById(R.id.ImgProfileId);
    }
}
