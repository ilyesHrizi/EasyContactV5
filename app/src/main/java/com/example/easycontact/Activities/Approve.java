package com.example.easycontact.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easycontact.R;

public class Approve extends AppCompatActivity {
TextView Mail , Pass;
Button Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        Mail = findViewById(R.id.tv_mailA);
        Pass = findViewById(R.id.tv_motpass);
        Home = findViewById(R.id.bt_home);
        Intent j = getIntent();
        final String cin = j.getStringExtra("Cin");
        final String email = j.getStringExtra("Email");
        Mail.setText(email);
        Pass.setText(cin);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
    }
}