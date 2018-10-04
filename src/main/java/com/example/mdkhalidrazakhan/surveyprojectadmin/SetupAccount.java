package com.example.mdkhalidrazakhan.surveyprojectadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetupAccount extends AppCompatActivity {
    private Button Save;
    private EditText nameInput,mobileInout;
    private  String name,mobile ,currentUser;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);
        Save = (Button) findViewById(R.id.setupSave);
        nameInput = (EditText) findViewById(R.id.setupName);
        mobileInout = (EditText) findViewById(R.id.setupMobile);
        progressBar = (ProgressBar) findViewById(R.id.loader);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser =firebaseAuth.getCurrentUser();
        currentUser = firebaseUser.getUid().toString();
        Save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
        name = nameInput.getText().toString().trim();
        mobile = mobileInout.getText() .toString();
        if(TextUtils.isEmpty(name))
        {

        }else if(TextUtils.isEmpty(mobile))
        {

        }
        else{
            progressBar.setVisibility(View.VISIBLE);
        }

                    }
                }
        );
    }
}
