package com.example.easycontact.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easycontact.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {
    EditText Cin,Email,Name,Lastname;
    Button BtnRegister;
    ProgressBar loadingProgress ;
    DatabaseReference databaseReference;
    FirebaseAuth mFirebase;
    static  int PreqCode = 1 ;
    static int REQUESTCODE = 1 ;
    ImageView imgProfile;
    Uri pickedImage;
    String photo;
    Map<String,String> taskMap;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Cin = findViewById(R.id.in_cin);
        Email = findViewById(R.id.in_mailadressuser);
        Name = findViewById(R.id.in_nameuser);
        Lastname = findViewById(R.id.in_lastnameuser);
        BtnRegister = findViewById(R.id.btn_register);
        imgProfile = findViewById(R.id.imgProfile);
        loadingProgress = findViewById(R.id.progressBar2);
        mFirebase = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(RegisterUser.this);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >=24)
                {
                    checkPermissionRequeste();
                }
                else
                {
                    openGallery();
                }
            }
        });
        FirebaseApp.initializeApp(RegisterUser.this);

       databaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebase = FirebaseAuth.getInstance();
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                  final String   photo =  pickedImage.toString();
                    if(pickedImage == null)
                    {
                        Toast.makeText(getApplicationContext(),"Choose picture",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        updateUserInfo( pickedImage ,mFirebase.getCurrentUser());
                    }


            }
        });
    }
    public String ValidateFieldString(String name , String lastname ,String Email , String Password){
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(Email);
        Integer CheckPassword = Integer.parseInt(Password);
        if (name.isEmpty())
        {
            return "name  is empty";

        }
        else if (lastname.isEmpty())
        {
            return "last name is empty";

        }

        else if (Email.isEmpty())
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
            return "valid field for register";
        }
    }
    public String checkUserExist(List<user> users , String Email){
        String result ="email exist you can't Register";
        for(int i= 0 ;i<users.size();i++){
            if(users.get(i).getEmail().equals(Email))
            {
                result= "email not exist register complete";
            }

        }
        return result;
    }
    private void updateUserInfo(  Uri pickedImgUri, final FirebaseUser currentUser) {
        BtnRegister.setVisibility(View.INVISIBLE);
        loadingProgress.setVisibility(View.VISIBLE);
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        photo = uri.toString();


                        final String name = Name.getText().toString();
                        final String lastname = Lastname.getText().toString();
                        final String cin = Cin.getText().toString();
                        final String email = Email.getText().toString();

                        if(name.isEmpty()&&cin.isEmpty()&&lastname.isEmpty()&&email.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(),"fields Empty",Toast.LENGTH_LONG).show();

                            BtnRegister.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);

                        }else

                        {

                            if(email.isEmpty())
                            {
                                Email.setError("Please enter  E-mail");
                                BtnRegister.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);

                            } else if( email.matches(emailPattern) && email.length() > 12)
                               {
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

                                      databaseReference.orderByChild("cin").equalTo(cin)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {

                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                Cin.setError("Enter New CIN/N°Passport this may be exist");
                                                //  Cin.setError("Please enter CIN");
                                                BtnRegister.setVisibility(View.VISIBLE);
                                                loadingProgress.setVisibility(View.INVISIBLE);
                                            } else {
                                                databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

                                                databaseReference.orderByChild("Email").equalTo(email)
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {

                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    Email.setError("Enter New E-mail this may be exist");
                                                                    BtnRegister.setVisibility(View.VISIBLE);
                                                                    loadingProgress.setVisibility(View.INVISIBLE);
                                                                }
                                                                else
                                                                {
                                                                    if(name.isEmpty())
                                                                    {
                                                                        Name.setError("Please enter name");
                                                                        BtnRegister.setVisibility(View.VISIBLE);
                                                                        loadingProgress.setVisibility(View.INVISIBLE);
                                                                    }
                                                                    if(lastname.isEmpty())
                                                                    {
                                                                        Lastname.setError("Please enter name");
                                                                        BtnRegister.setVisibility(View.VISIBLE);
                                                                        loadingProgress.setVisibility(View.INVISIBLE);
                                                                    }
                                                                    if(cin.isEmpty() && cin.length()!= 8 )
                                                                    {
                                                                        Cin.setError("Please enter CIN He must be contains 8 numbers");
                                                                        BtnRegister.setVisibility(View.VISIBLE);
                                                                        loadingProgress.setVisibility(View.INVISIBLE);
                                                                    }
                                                                    else{
                                                                        databaseReference = FirebaseDatabase.getInstance().getReference();

                                                                        Map<String, Object> user = new HashMap<>();
                                                                        user.put("cin",cin);
                                                                        user.put("Email",email);
                                                                        user.put("Name",name);
                                                                        user.put("Lastname",lastname);
                                                                        user.put("Approve","false");
                                                                        user.put("Statut","Offline");
                                                                        user.put("photo"  , uri.toString() );
                                                                        databaseReference.child("User").child(cin).setValue(user);
                                                                        Toast.makeText(RegisterUser.this,"Register succeful",Toast.LENGTH_SHORT).show();
                                                                        mFirebase.createUserWithEmailAndPassword(email , cin).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                if(task.isSuccessful())
                                                                                {
                                                                                    Intent i = new Intent(getApplicationContext(),Approve.class);
                                                                                    i.putExtra("Email",email);
                                                                                    i.putExtra("Cin",cin);
                                                                                    startActivity(i);
                                                                                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(cin)
                                                                                            .setPhotoUri(uri)
                                                                                            .build();
                                                                                    mFirebase.getCurrentUser().updateProfile(profileUpdate)
                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    if(task.isSuccessful()){
                                                                                                    }
                                                                                                }
                                                                                            });
                                                                                }

                                                                            }
                                                                        });


                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                        }
                            else{

                                Email.setError("Email need contain a .. z and 0 .. 9 ");
                            BtnRegister.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                            } }
                    }
                });
            }
        });

    }
    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);
    }

    private void checkPermissionRequeste() {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterUser.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterUser.this ,"please accepte permission ",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RegisterUser.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PreqCode);
            }
        }
        else
        {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null ){

            pickedImage = data.getData();
            imgProfile.setBackgroundColor(0);
            imgProfile.setImageURI(pickedImage);
        }
    }

}
