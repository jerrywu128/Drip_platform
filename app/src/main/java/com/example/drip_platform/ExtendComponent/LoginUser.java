package com.example.drip_platform.ExtendComponent;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class LoginUser extends AsyncTask<String,Void,String> {
    static int a = 0;
    public static OkHttpClient client = new OkHttpClient();
    public static boolean login(String account, String password, Context L) throws MalformedURLException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection;
                    OutputStream outputStream;
                    URL url = new URL("http://203.64.128.65:81/login");
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setUseCaches(false);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);


                    final String BOUNDARY = "******************";
                    final String twoHyphens = "--";
                    final String crlf = "\r\n";

                    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

                    outputStream = connection.getOutputStream();

                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                    writer.append(twoHyphens + BOUNDARY).append(crlf);

                    writer.append("Content-Disposition: form-data; name=\"password\"").append(crlf);
                    writer.append("Content-Type: text/plain; charset=UTF-8").append(crlf);
                    writer.append(crlf);
                    writer.append(password).append(crlf);
                    writer.append(twoHyphens + BOUNDARY).append(crlf);
                    writer.append("Content-Disposition: form-data; name=\"username\"").append(crlf);
                    writer.append("Content-Type: text/plain; charset=UTF-8").append(crlf);
                    writer.append(crlf);
                    writer.append(account).append(crlf);
                    List<String> response = new ArrayList<String>();
                    writer.append(twoHyphens + BOUNDARY + twoHyphens).append(crlf);
                    writer.flush();
                    writer.close();

                    int status = connection.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.add(line);
                        }

                        System.out.println("response = "+response.toString().indexOf("sucess"));
                        a = response.toString().indexOf("sucess");

                        reader.close();
                        connection.disconnect();
                    } else {
                        System.out.println("ooooo");
                    }
                } catch (Exception e) {
                    System.out.println("URL_ERROR");
                }
            }});
        thread.start();

        try{
            // delay 1 second
            Thread.sleep(100);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        if(a<0){
            return false;
        }else {
            return true;
        }


    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }
}