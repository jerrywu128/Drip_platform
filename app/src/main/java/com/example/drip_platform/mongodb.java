package com.example.drip_platform;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class mongodb {
    String Original_data;

    public String [] show () {
        GetURLData();
        String a = "\" "+ Original_data + "\"";   //""date": "13:04:28","value": 6 "
        a = a.replace(" ","");
        String z = a.replace("}","");
        String b = z.replace("\"",",");
        String x = b.replace("{","");
        String [] c = x.split(",");

        try {
            System.out.println(Original_data);
            System.out.println(c[4]);
            System.out.println(c[8]);
        }catch (Exception e) {
            System.out.println("error");
        }
        return c;
    }

    public void GetURLData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Original_data = "";
                String decode;


                try {
                    URL u = new URL("http://203.64.128.65/data_list");

                    HttpURLConnection hc = (HttpURLConnection) u.openConnection();
                   try{
                       //hc.setRequestMethod("GET");
                       hc.setDoInput(true);
                       //hc.setDoOutput(true);
                       hc.connect();

                       BufferedReader in = new BufferedReader(new InputStreamReader(hc.getInputStream()));


                       while ((decode = in.readLine()) != null) {
                           Original_data += decode;

                       }
                       in.close();
                   }finally {
                       hc.disconnect();
                   }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        });
        thread.start();
    }

}
