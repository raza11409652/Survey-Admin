package com.example.mdkhalidrazakhan.surveyprojectadmin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.example.mdkhalidrazakhan.surveyprojectadmin.model.DataBaseHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.formula.functions.Na;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class ExportExcel extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,userName;
    FirebaseUser firebaseUser;
    DataBaseHelper dataBaseHelper;
    Button downloadBtn;
    Boolean isInserted=false;
    SQLiteToExcel sqLiteToExcel;
    Boolean permission_write;
     ProgressDialog progressDialog;
     String nameUser="None";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_excel);

        setTitle("Export Excel File");
//connect firabse database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference().child("Data");
        userName = firebaseDatabase.getReference().child("Users");

        //connecting to sqllite
        dataBaseHelper = new DataBaseHelper(this);

         permission_write=isStoragePermissionGranted();
         //progress dialog
        progressDialog=new ProgressDialog(this);
        //on create copy all the data from firebase to sql lite and
        /*
        * when user click download button export data from sql lite and delete all the data from sql lite
        * file name must be file_name.currentDateTimeStamp.xls
        * */
        downloadBtn=(Button)findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait File is being Exported");
                progressDialog.setCanceledOnTouchOutside(false);


                if(permission_write ==true)
                {
                    progressDialog.show();
                    //start Progress bar
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //fetch all the data in string Format and  Store in Sql Lite data base
//                String name= dataSnapshot.getValue().toString();
                            for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                            {



                                String name=dataSnapshot1.child("Name").getValue().toString();
                                String sex=dataSnapshot1.child("Sex").getValue().toString();
                                String caste=dataSnapshot1.child("Caste").getValue().toString();
                                String Age =dataSnapshot1.child("Age").getValue().toString();
                                String community=dataSnapshot1.child("Community").getValue().toString();
                                String profession=dataSnapshot1.child("Profession").getValue().toString();
                                String AddMandal=dataSnapshot1.child("AddMandal").getValue().toString();
                                String AddVill=dataSnapshot1.child("AddVill").getValue().toString();
                                String AddSubVill =dataSnapshot1.child("AddSubVill").getValue().toString();
                                String AddPin=dataSnapshot1.child("AddPin").getValue().toString();
                                String AddMob=dataSnapshot1.child("AddMob").getValue().toString();
                                String Rating=dataSnapshot1.child("Rating").getValue().toString();
                                String Goverment=dataSnapshot1.child("Goverment").getValue().toString();
                                String Lat=dataSnapshot1.child("Lat").getValue().toString();
                                String Log=dataSnapshot1.child("Log").getValue().toString();
                                String FullAddress=dataSnapshot1.child("FullAddress").getValue().toString();
                                String ConstTdp=dataSnapshot1.child("ConstTdp").getValue().toString();
                                String ConstPrefRating=dataSnapshot1.child("ConstPrefRating").getValue().toString();
                                String Date=dataSnapshot1.child("Date").getValue().toString();
                                String By = dataSnapshot1.child("By").getValue().toString();


                                isInserted=dataBaseHelper.insertData(AddMandal,AddSubVill,FullAddress,AddMob,Log,sex,Rating,AddPin,Date,name,ConstPrefRating,profession,Goverment,AddVill,caste,Age,community,Lat,ConstTdp,By);
                                //export in Excel file


                            }
                            if(isInserted ==true)
                            {


                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                String file_export=currentDateTimeString.substring(0,6);
                                //currentDateTimeString.replace(',','_');
                                //currentDateTimeString.replace(':','_');
                                String File_name="Survey_report"+file_export+".xls";
                                String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Survey Report/";




                                File file = new File(directory_path);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                sqLiteToExcel= new SQLiteToExcel(getApplicationContext() , dataBaseHelper.DB_NAME,directory_path);
                                sqLiteToExcel.exportSingleTable("survey_report", File_name, new SQLiteToExcel.ExportListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onCompleted(String filePath) {

                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext() , "File has been Exported ",Toast.LENGTH_SHORT).show();

                                        //delete data from sqLite
                                        dataBaseHelper.deleteAllData();
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext() ,e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                                //if permission write



                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Provide Permission",Toast.LENGTH_SHORT).show();
                }



            }
        });

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
}
