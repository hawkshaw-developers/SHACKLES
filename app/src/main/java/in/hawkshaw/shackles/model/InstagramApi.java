package in.hawkshaw.shackles.model;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InstagramApi extends AsyncTask<String , String , String > {


    @Override
    protected String doInBackground(String... strings) {



        try {
            URL url = new URL(Constant.ACCESS_TOKEN_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            String urlParameters = Constant.PARAM_CLIENT_ID+"&"+Constant.CLIENT_SECRET+"&"+Constant.PARAM_AUTH_CODE+"&"+Constant.PARAM_REDIRECT_URI+"&"+Constant.PARAM_CODE+strings[0];
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Log.d("insta details",strings[0]);
            URL url = new URL(Constant.INSTA_BASIC+strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String response = connection.getResponseMessage();
            Log.d("insta details" , response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
