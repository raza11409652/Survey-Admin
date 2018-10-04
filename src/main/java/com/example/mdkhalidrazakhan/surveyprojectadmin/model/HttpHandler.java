package com.example.mdkhalidrazakhan.surveyprojectadmin.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandler {
    public HttpHandler() {
    }

    public String GetHttp(String Url)
    {
        URL url;
        String Response = "";
        try {
            {
                url=new URL(Url);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(100000);
                connection.setConnectTimeout(100000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                connection.setDoOutput(true);
                int responseCode=connection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK)
                {
                    String line;
                    BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = bufferedReader.readLine())!=null)
                    {
                        Response+=line;
                    }
                }
                else{
                    Response ="";
                }

            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response;
    }
}
