package in.hawkshaw.shackles.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParserHelper {
    private String json;
    JsonParserHelper(String json){
        this.json = json;
    }

    public static ArrayList<String> getUrlsFromRecentMedia(String json){
        ArrayList<String> urlArray = new ArrayList<>();
        try {
            JSONArray array = new JSONObject(json).getJSONArray("data");
            for(int i =0 ; i < array.length() ; i++){
                JSONObject object = (JSONObject) array.get(i);
                urlArray.add( object.getJSONObject("images").getJSONObject("standard_resolution").getString("url") );

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return urlArray;
    }
}
