package com.example.easycontact.Activities;

import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycontact.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    ProgressBar loginProgress;
    TextView  créeUnCompte;
    EditText ID , Motpass;
    FirebaseAuth mFirebase;
    private FirebaseAuth.AuthStateListener mAuthL;
    private Intent HomeActivity;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.loginBtn);
        loginProgress = findViewById(R.id.login_progress);
        ID = findViewById(R.id.in_mail);
        Motpass = findViewById(R.id.in_pass);
       //  HomeActivity = new Intent(this,LoginActivity.class);
        créeUnCompte = findViewById(R.id.tv_account);
        créeUnCompte.setOnClickListener(this);
        mFirebase = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = ID.getText().toString();
                final String pwd = Motpass.getText().toString();
                if (email.isEmpty()) {
                    ID.setError("Please entre email ");
                } else if (pwd.isEmpty()) {
                    Motpass.setError("please entre password");
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "field are empty", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebase.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "sign up unsucceful please try again", Toast.LENGTH_SHORT).show();


                            } else {

                                loginProgress.setVisibility(View.INVISIBLE);
                                btnLogin.setVisibility(View.VISIBLE);
                            //    updateUI();
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                i.putExtra("cin",pwd);
                                startActivity(i);
                            }
                        }
                    });
                }

            }





        });
    }

public String ValidateFieldString(String Email , String Password){
    Pattern pattern = Pattern.compile(emailPattern);
    Matcher matcher = pattern.matcher(Email);
    Integer CheckPassword = Integer.parseInt(Password);
        if (Email.isEmpty())
        {
            return "email is empty";

        }
        else if (!(matcher.matches()))
        {
            return "invalid Email";

        }
        else if (Password.isEmpty())
        {
            return "password is empty";

        }
        else if (Password.length()!=8)
        {
            return "password is invalid should have 8 caraccter";
        }
        else if (CheckPassword<0){
            return "password should get only number";
        }
        else
        {
            return "valid field for login";
        }
}
public String checkUserExist(List<user> users ,String Email){
        String result ="email not exist";
        for(int i= 0 ;i<users.size();i++){
            if(users.get(i).getEmail().equals(Email))
            {
                result= "email exist you can login";
            }
                }
        return result;
}
    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_account)
        {
            Intent i = new Intent(getApplicationContext(),RegisterUser.class);
            startActivity(i);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mFirebase.getCurrentUser();


    }
}