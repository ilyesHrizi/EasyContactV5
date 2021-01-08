package com.example.easycontact.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.example.easycontact.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

String cin ;
List<user> list;
    Map<String,String> taskMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent j = getIntent();
          cin =  j.getStringExtra("cin");
      databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(cin);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    taskMap = new HashMap<String, String>();
                    taskMap.put(postSnapshot.getKey(), postSnapshot.getValue().toString());
                    System.out.println("Boucle while");
                    Iterator iterator = taskMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry mapentry = (Map.Entry) iterator.next();

                        if(mapentry.getKey().equals("Approve"))
                        {
                            System.out.println("clé: " + mapentry.getKey()
                                    + " | valeur: " + mapentry.getValue().toString());
                            if(mapentry.getValue().equals("true"))
                            {
                                Intent i = new Intent(getApplicationContext(),NavigationActivity.class);
                                i.putExtra("cin",cin);
                                startActivity(i);
                            }
                            else {

                                Intent i = new Intent(getApplicationContext(),WaitingActivity.class);
                                startActivity(i);
                            }
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}