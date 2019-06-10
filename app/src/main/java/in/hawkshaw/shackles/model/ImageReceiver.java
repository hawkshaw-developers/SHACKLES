package in.hawkshaw.shackles.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class ImageReceiver extends AsyncTask<String , Void , Bitmap> {
private ImageReceiverResponse receiverResponse;

        public ImageReceiver(ImageReceiverResponse response){
        receiverResponse = response;
        }
public interface ImageReceiverResponse{
    void imageResponse(Bitmap map);
}
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            return BitmapFactory.decodeStream(connection.getInputStream());
        }
        catch(Exception e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        receiverResponse.imageResponse(bitmap);
    }
}