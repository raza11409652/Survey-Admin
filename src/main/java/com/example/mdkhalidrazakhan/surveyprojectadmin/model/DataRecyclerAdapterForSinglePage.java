package com.example.mdkhalidrazakhan.surveyprojectadmin.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mdkhalidrazakhan.surveyprojectadmin.R;

public class DataRecyclerAdapterForSinglePage extends RecyclerView.ViewHolder {
    View view;
    public DataRecyclerAdapterForSinglePage(View itemView) {
        super(itemView);
        view=itemView;
    }

 public void setDate(String Date)
    {
        TextView textView = (TextView)view.findViewById(R.id.surveyTitle);
        textView.setText(Date);
    }
    public void setBy(String name)
    {
        TextView textView=(TextView)view.findViewById(R.id.SurveyBy);
        textView.setText(name);
    }
    public void setAge(String Age)
    {
        TextView textView=(TextView)view.findViewById(R.id.respodentAge);
        textView.setText(Age);
    }
    public void setGender(String Gender){
        TextView textView=(TextView)view.findViewById(R.id.respodentSex);
        textView.setText(Gender);
    }
    public  void setCaste(String Caste)
    {
        TextView textView = (TextView)view.findViewById(R.id.respodentCaste);
        textView.setText(Caste);
    }
    public  void setCommunity(String Community){
        TextView textView = (TextView)view.findViewById(R.id.respodentCommunity);
        textView.setText(Community);
    }
    public void setProfession(String Profession)
    {
        TextView textView = (TextView)view.findViewById(R.id.respodentProfession);

        textView.setText(Profession);
    }
    public  void setMandal(String Mandal)
    {
        TextView textView =(TextView)view.findViewById(R.id.respodentMandal);
        textView.setText(Mandal);
    }
    public void setVillage(String Village)
    {
        TextView textView = (TextView)view.findViewById(R.id.respodentVillage);
        textView.setText(Village);
    }
    public void setSubVillage(String Subvillage)
    {
        TextView textView =(TextView)view.findViewById(R.id.respodentSubVillage);
        textView.setText(Subvillage);
    }
    public void setPin(String Pin)
    {
        TextView textView= (TextView)view.findViewById(R.id.respodentPin);
        textView.setText(Pin);
    }
    public void setRating(String Value)
    {
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.respodentRating);
        Float d;
        d=Float.parseFloat(Value);
        ratingBar.setRating(d);
    }
    public void setGoverment(String Govt)
    {
        TextView textView = (TextView)view.findViewById(R.id.respodentGovt);
        textView.setText(Govt);
    }
    public  void setrespodentName(String Name)
    {
        TextView textView =(TextView)view.findViewById(R.id.respodentName);
        textView.setText(Name);
    }

}

