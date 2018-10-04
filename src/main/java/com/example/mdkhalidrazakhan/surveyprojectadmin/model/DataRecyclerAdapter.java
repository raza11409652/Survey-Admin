package com.example.mdkhalidrazakhan.surveyprojectadmin.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mdkhalidrazakhan.surveyprojectadmin.R;

import org.w3c.dom.Text;

public class DataRecyclerAdapter extends RecyclerView.ViewHolder{
    View view;

    public DataRecyclerAdapter(View itemView) {

        super(itemView);

        view=itemView;


    }
    public void SetDate(String Date){
        TextView fetchData=(TextView)view.findViewById(R.id.surveyTitle);
        fetchData.setText(Date);
        //return Date;
    }
    public  void SetBy(String name){
        TextView userName= (TextView)view.findViewById(R.id.SurveyBy);
        userName.setText(name);
    }
}