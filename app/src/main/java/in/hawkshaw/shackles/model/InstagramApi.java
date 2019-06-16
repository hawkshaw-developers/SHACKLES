package in.hawkshaw.shackles.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import in.hawkshaw.shackles.model.InstagramApi.AsyncResponse;

public class InstagramApi {


    public interface AsyncResponse{
        String getResponse(String result);
    }

    public interface RecentMediaAsync{
        void getMediaObject(String result);
    }

    public String getDataAccessToken(String code , InstagramApi.AsyncResponse response){

        InstagramDataTokenAsync accessAsync = new InstagramDataTokenAsync(code , response);
        accessAsync.execute(code);
        return null;
    }

    public void getRecentMediaAsync(String access , InstagramApi.RecentMediaAsync recentMediaAsync){
        InstagramRecentMediaAsync async = new InstagramRecentMediaAsync(recentMediaAsync);
        async.execute(access);
    }
}


class InstagramDataTokenAsync extends AsyncTask<String , Void , String>{

    String code;
    AsyncResponse response;
    InstagramDataTokenAsync(String code , AsyncResponse response){
        this.response = response;
        this.code = code;
    }
    @Override
    protected String doInBackground(String... strings) {
        Log.d("INSTA API",strings[0]);
        try {

            URL url = new URL(Constant.ACCESS_TOKEN_URL); // here is your URL path
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("client_id", Constant.CLIENT_ID);
            postDataParams.put("client_secret", Constant.CLIENT_SECRET);
            postDataParams.put("grant_type","authorization_code");
            postDataParams.put("redirect_uri",Constant.REDIRECT_URI);
            postDataParams.put("code",code);
            Log.d("INSTA API",NetworkFunctions.getPostDataString(postDataParams));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(NetworkFunctions.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();
            Log.d("INSTA API",Integer.toString(responseCode));
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                Log.d("INSTA API" , sb.toString());
                return sb.toString();

            }
            else {
                Log.d("INSTA API" , responseCode+" failed");
                return "false : " + responseCode;
            }
        }
        catch(Exception e){
            return "Exception: " + e.getMessage();
        }
    }



    @Override
    protected void onPostExecute(String s) {
        response.getResponse(s);
    }
}


class InstagramRecentMediaAsync extends AsyncTask<String , Void , String>{

    InstagramApi.RecentMediaAsync async;

    InstagramRecentMediaAsync(InstagramApi.RecentMediaAsync async){
        this.async = async;
    }




    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(Constant.INSTA_RECENT_MEDIA_URL + strings[0]);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            int responseCode=conn.getResponseCode();
            Log.d("INSTA API",Integer.toString(responseCode));
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line;
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                Log.d("INSTA API" , sb.toString());
                return sb.toString();

            }
            else {
                Log.d("INSTA API" , responseCode+" failed");
                return "false : " + responseCode;
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        async.getMediaObject(s);
    }
}

