package com.example.easycontact.Adapters;

import android.content.Context;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.easycontact.Models.Comment;
import com.example.easycontact.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context mContext;
    private List<Comment> mData;
    DatabaseReference databaseReference;

    Map<String,String> taskMap;

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.row_comment,parent,false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(mData.get(position).getUname());
        databaseReference.addValueEventListener(new ValueEventListener() {
            String name,uri;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    taskMap = new HashMap<String, String>();
                    taskMap.put(postSnapshot.getKey(), postSnapshot.getValue().toString());
                    System.out.println("Boucle while");
                    Iterator iterator = taskMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry mapentry = (Map.Entry) iterator.next();

                        if(mapentry.getKey().equals("photo"))
                        {

                            uri = mapentry.getValue().toString();
                            System.out.println(uri);

                            Glide.with(mContext).load(uri).into(holder.img_user);

                        }
                        if(mapentry.getKey().equals("Name"))
                        {

                            name = name + " " +mapentry.getValue().toString();
                            System.out.println(name);
                            holder.tv_name.setText(name);

                        }
                        if(mapentry.getKey().equals("Lastname"))
                        {
                     /*   System.out.println("clé: " + mapentry.getKey()
                                + " | valeur: " + mapentry.getValue().toString());*/
                            name ="  "+mapentry.getValue().toString();
                            System.out.println(name);


                        }

                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.tv_content.setText(mData.get(position).getContent());
        holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView img_user;
        TextView tv_name,tv_content,tv_date;

        public CommentViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.comment_user_img);
            tv_name = itemView.findViewById(R.id.comment_username);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_date = itemView.findViewById(R.id.comment_date);
        }
    }



    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm",calendar).toString();
        return date;


    }


}
