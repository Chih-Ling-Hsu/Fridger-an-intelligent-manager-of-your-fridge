package com.mysampleapp.demo.service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by User on 2017/6/9.
 */

public class HttpURLConnectionHandler {

    private final String USER_AGENT = "Mozilla/5.0";
    public StringBuffer response = null;
    public boolean complete = false;

    public String getResponse(){
        return response.toString();
    }

    // HTTP GET request
    public void sendGet(String url) throws Exception {

        //String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        Log.e("APIIIII","\nSending 'GET' request to URL : " + url);
        Log.e("APIIIII","Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Log.e("APIIIII",response.toString());
        complete = true;
    }

    // HTTP POST request
    public boolean sendPost(String url, String postMsg) throws Exception {
        complete = false;
        //String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        con.setRequestProperty("Content-Type", "application/json");
        //con.setRequestProperty( "charset", "utf-8");

        //String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        String body = postMsg;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Log.e("APIIIII","\nSending 'POST' request to URL : " + url);
        Log.e("APIIIII","Post parameters : " + body);
        Log.e("APIIIII","Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Log.e("APIIIII OK",response.toString());
        complete = true;
        return true;

    }

}