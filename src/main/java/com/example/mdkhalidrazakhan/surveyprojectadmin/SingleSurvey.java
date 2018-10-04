package com.example.mdkhalidrazakhan.surveyprojectadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mdkhalidrazakhan.surveyprojectadmin.model.Data;
import com.example.mdkhalidrazakhan.surveyprojectadmin.model.DataRecyclerAdapterForSinglePage;
import com.example.mdkhalidrazakhan.surveyprojectadmin.model.HttpHandler;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleSurvey extends AppCompatActivity {
private DatabaseReference databaseReference , userDatabaseref;
private FirebaseDatabase firebaseDatabase;
private RecyclerView recyclerView;
double lat,log;
ProgressDialog progressDialog;
private FirebaseUser firebaseUser;
private FirebaseAuth firebaseAuth;

TextView dateText,submitText,resName,resGender,resAge,resCaste,resCom,resProf,resMandal,resVill,resSubVill,resPin,resMob,resGovt,CompleteLocation;
TextView whoisconst,whoisConstPref;
RatingBar resRating;
    public String []Arr=new String[20];
    public int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_survey);
        Intent intent= getIntent();
        String currentKey=intent.getStringExtra("CurrentId");
        //Toast.makeText(getApplicationContext(),"key : "+currentKey,Toast.LENGTH_SHORT).show();
        //text view to display data
        dateText = (TextView)findViewById(R.id.surveyTitle);
        submitText=(TextView)findViewById(R.id.SurveyBy) ;
        resMob=(TextView)findViewById(R.id.respodentMob);
        resName = (TextView)findViewById(R.id.respodentName);
        resGender = (TextView)findViewById(R.id.respodentSex);
        resAge = (TextView)findViewById(R.id.respodentAge);
        resCaste = (TextView)findViewById(R.id.respodentCaste);
        resCom = (TextView)findViewById(R.id.respodentCommunity);
        resProf = (TextView) findViewById(R.id.respodentProfession);
        resVill = (TextView) findViewById(R.id.respodentVillage);
        resSubVill = (TextView) findViewById(R.id.respodentSubVillage);
        resPin = (TextView) findViewById(R.id.respodentPin);
        resRating = (RatingBar)findViewById(R.id.respodentRating);
        resMandal = (TextView)findViewById(R.id.respodentMandal);
        resGovt = (TextView) findViewById(R.id.respodentGovt);
        firebaseDatabase=FirebaseDatabase.getInstance();
        CompleteLocation = (TextView)findViewById(R.id.CompleteLocation);

        whoisconst = (TextView)findViewById(R.id.whoisConst);
        whoisConstPref = (TextView)findViewById(R.id.whoisConstPref);
        databaseReference=firebaseDatabase.getReference().child("Data").child(currentKey);
        progressDialog = new ProgressDialog(this);

        setTitle("Survey : "+currentKey);
    //firebase recycler adapter

    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        String AddMandal=dataSnapshot.child("AddMandal").getValue().toString();
        resMandal.setText("Mandal : "+AddMandal);
        String AddMob= dataSnapshot.child("AddMob").getValue().toString();
        resMob.setText("Mobile Number +91-"+AddMob);
        String AddPin=dataSnapshot.child("AddPin").getValue().toString();
        resPin.setText("Pin code / Zip Code : " + AddPin);
        String AddSubvill = dataSnapshot.child("AddSubVill").getValue().toString();
        resSubVill.setText("Sub Village : "+AddSubvill);
        String AddVill=dataSnapshot.child("AddVill").getValue().toString();
        resVill.setText("Village :" + AddVill);
        String Age=dataSnapshot.child("Age").getValue().toString();
        resAge.setText("Age: "+Age);
        String SubmitBy=dataSnapshot.child("By").getValue().toString();
           // submitText.setText(SubmitBy);
            submitText.setText(SubmitBy);
            String Caste =dataSnapshot.child("Caste").getValue().toString();
            resCaste.setText(Caste);
            String Com=dataSnapshot.child("Community").getValue().toString();
            resCom.setText(Com);
            String SubmitedOn =dataSnapshot.child("Date").getValue().toString();
            dateText.setText(SubmitedOn);
            String FullAddress = dataSnapshot.child("FullAddress").toString();
            String Goverment = dataSnapshot.child("Goverment").getValue().toString();
            resGovt.setText(Goverment);
            String Lat=dataSnapshot.child("Lat").getValue().toString();
            lat=Double.parseDouble(Lat);

            String Log=dataSnapshot.child("Log").getValue().toString();
                log=Double.parseDouble(Log);


            new GetClass().execute(String.format("%.4f , %.4f",lat,log));

            String Name=dataSnapshot.child("Name").getValue().toString();
            resName.setText(Name);
            String Profession= dataSnapshot.child("Profession").getValue().toString();
            resProf.setText(Profession);
            String Rating = dataSnapshot.child("Rating").getValue().toString();
            resRating.setRating(Float.parseFloat(Rating));
            String Gender=dataSnapshot.child("Sex").getValue().toString();
            resGender.setText(Gender);

       // Toast.makeText(getApplicationContext() , SubmitBy , Toast.LENGTH_SHORT).show();
            //fetch
            String ConstPrefRating = "0.00";
           ConstPrefRating  = dataSnapshot.child("ConstPrefRating").getValue().toString();
           if(ConstPrefRating.equals(" "))
           {
               ConstPrefRating = "None";
           }
            whoisConstPref.setText("Who is Constituency Preferences : "+ConstPrefRating +"/5.0");
            String  ConstTdp = "None";
             ConstTdp= dataSnapshot.child("ConstTdp").getValue().toString();
            if(ConstTdp.equals(" "))
            {
                ConstTdp ="None";
            }
            whoisconst.setText("Who is constituency : "+ConstTdp);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });


    }



    //getting location
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
                String url=String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%.4f,%.4f",lat,log);

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
               // Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();
                CompleteLocation.setText(address);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
