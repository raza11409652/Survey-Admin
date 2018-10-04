package com.example.mdkhalidrazakhan.surveyprojectadmin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mdkhalidrazakhan.surveyprojectadmin.model.Data;
import com.example.mdkhalidrazakhan.surveyprojectadmin.model.DataBaseHelper;
import com.example.mdkhalidrazakhan.surveyprojectadmin.model.DataRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import javax.annotation.Nullable;

public class Dashboard extends AppCompatActivity {

    public String[] name  =new String[3];

private Boolean Write_permission;

    private static final String TAG = "";
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    RecyclerView recyclerView;
    DataBaseHelper dataBaseHelper;
    public List<Data> data;
    private DataRecyclerAdapter dataRecyclerAdapter;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Write_permission=isStoragePermissionGranted();
        name[2]="Submitted By";
        setTitle("HOME");
        String Url="https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452";
        String response = null;
       /* dataBaseHelper=new DataBaseHelper(this);
        Cursor cursor=dataBaseHelper.getAllData();
        if(cursor.getCount()  ==0)
        {
            showMessage("Error","Nodata Found");
        }else{
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("Id :"+ cursor.getString(0)+"\n");
                buffer.append("Name :"+ cursor.getString(1)+"\n");
                buffer.append("Surname :"+ cursor.getString(2)+"\n");
                buffer.append("Marks :"+ cursor.getString(3)+"\n\n");
            }

            // Show all data
            showMessage("Data",buffer.toString());
        }
        */
        //Firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Data");

        firebaseFirestore=FirebaseFirestore.getInstance();
        // View view=View.inflate()
        data=new ArrayList<>();

        recyclerView=findViewById(R.id.surveyListContainer);

      //  dataRecyclerAdapter = new DataRecyclerAdapter(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataStart();

    }

    private void showMessage(String error, String nodata_found) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(error);
        builder.setMessage(nodata_found);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.admin_nav,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.admin_nav_logout)
        {
            //logout admin
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if(firebaseUser !=null)
            {
                firebaseAuth.signOut();
                Intent logout=new Intent(getApplicationContext() , Login.class);
                startActivity(logout);
                finish();
            }
        }
        else if(id==R.id.admin_nav_export_excel)
        {
            //start intent to exporting File into Excel

            Intent intent = new Intent(getApplicationContext() ,ExportExcel.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();

      dataStart();

}

    @Override
    protected void onRestart() {
        super.onRestart();
        dataStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataStart();
    }

    public String getUserName(final String user){

        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1=firebaseDatabase.getReference().child("Users").child(user);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    int i=0;

                    // Toast.makeText(getApplicationContext(),dataSnapshot.toString(),Toast.LENGTH_SHORT).show();
                    for (DataSnapshot userData: dataSnapshot.getChildren() )
                    {
                        // String Value=userData.child("Name").getValue().toString();
                        name[i]=userData.getValue().toString();
                        //Toast.makeText(getApplicationContext(),""+name[i],Toast.LENGTH_SHORT).show();
                        i++;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //return name[0];

        return name[2];
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Error : Permisiion","Permission is granted");
                return true;
            } else {

                Log.v("Eroor : Permission","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Permisison","Permission is granted");
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("Error","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private void dataStart() {
        FirebaseRecyclerAdapter  <Data , DataRecyclerAdapter> dataAdapter =
                new FirebaseRecyclerAdapter<Data, DataRecyclerAdapter>(
                        Data.class,
                        R.layout.single_page,
                        DataRecyclerAdapter.class,
                        databaseReference) {
                    @Override
                    protected void populateViewHolder(final DataRecyclerAdapter viewHolder, Data model, final int position) {
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String KeyId=getRef(position).getKey().toString();

                                //Toast.makeText(getApplicationContext(),KeyId,Toast.LENGTH_SHORT).show();
                                Intent SingleSurveyActivity= new Intent(getApplicationContext(),SingleSurvey.class);
                                SingleSurveyActivity.putExtra("CurrentId",KeyId);
                                startActivity(SingleSurveyActivity);

                            }
                        });
                        viewHolder.SetDate(model.getDate());
                        String UserName=model.getBy();
                        viewHolder.SetBy(model.getBy());


                        //viewHolder.SetBy(getUserName(model.getBy()));
//                Toast.makeText(getApplicationContext(),"from ViewHolder"+name[0],Toast.LENGTH_LONG).show();

                    }
                };
        recyclerView.setAdapter(dataAdapter);
    }
    }




