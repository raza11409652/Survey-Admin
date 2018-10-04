package com.example.mdkhalidrazakhan.surveyprojectadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mdkhalidrazakhan.surveyprojectadmin.model.HttpHandler;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Login extends AppCompatActivity {
    Button login;
    private EditText email,password;
    private String emailText,passwordText,currentUser;
   public double lat,log;
   ProgressBar progressBar;
   FirebaseUser firebaseUser;
   FirebaseFirestore firebaseFirestore;
   FirebaseDatabase firebaseDatabase;
   DatabaseReference databaseReference;
   ProgressDialog progressDialog;

    private String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login to your Account");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        email=(EditText)findViewById(R.id.email);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        password = (EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //progressBar.setProgress(10);
                emailText = email.getText().toString().trim();
                passwordText = password.getText().toString();
                if(TextUtils.isEmpty(emailText))
                {
                    //do something
                }
                else if(TextUtils.isEmpty(passwordText)){
                    //do something
                    Toast.makeText(getApplicationContext() , "Error : Email Is Required",Toast.LENGTH_SHORT).show();

                }
                else if(!emailText.matches(emailPattern))
                {
                    //do something
                   // Toast.makeText(getApplicationContext(), "Error : Email Is Not Valid").show();
                    Toast.makeText(getApplicationContext() ,"Erorr : Email is not Valid",Toast.LENGTH_SHORT).show();

                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(emailText , passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    firebaseUser =firebaseAuth.getCurrentUser();
                                    //check whether user is Admin Or not
                                    if(firebaseUser !=null)
                                    {
                                        currentUser =firebaseUser.getUid();
                                        //firebaseFirestore.collection("Users").
                                       firebaseDatabase = FirebaseDatabase.getInstance();
                                       databaseReference=firebaseDatabase.getReference().child("Users").child(currentUser);
                                       databaseReference.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                               //check for Admin Previlaged
                                               String isAdmin = dataSnapshot.child("IsAdmin").getValue().toString();
                                                if(isAdmin.equals("Yes"))
                                                {
                                                    //send To DashBoard else
                                                    progressBar.setVisibility(View.INVISIBLE);

                                                    Intent dash=new Intent(getApplicationContext() , Dashboard.class);
                                                    startActivity(dash);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext() ,  "Email is not Admin Privialged",Toast.LENGTH_SHORT).show();
                                                    firebaseAuth.signOut();
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                }

                                           }

                                           @Override
                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                           }
                                       });

                                    }



                                }else

                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext() , task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                        }
                    });
                }

            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();

        //check for User Logged in or not
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Validating User Login Cradential");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        firebaseAuth = FirebaseAuth.getInstance() ;
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase database;
        DatabaseReference databaseReference;
        database =FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");

        String Currentname;
        if(firebaseUser !=null)
        {
            Currentname =firebaseUser.getUid();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        progressDialog.dismiss();
                        Intent dash=new Intent(getApplicationContext() , Dashboard.class);
                        startActivity(dash);
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else{
            progressDialog.dismiss();
        }


    }
    private class GetClass extends AsyncTask<String , Void ,String >
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                HttpHandler httpHandler = new HttpHandler();
                String Response;
                String url=String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f&key=AIzaSyBd7mIrRHL3F2XyiPau3DZPvt79wMthDuA",lat,log);
                Response=httpHandler.GetHttp(url);
            return  Response;

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try
            {
                JSONObject jsonObject=new JSONObject(s);
                String address=((JSONArray)jsonObject.get("results")).getJSONObject(0).get("formatted_address").toString();
                 Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
