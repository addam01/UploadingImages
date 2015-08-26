package testaccountlogin.rounin_rave.com.uploadingimages;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.IOException;

/**
 * Created by IAT Dev on 24/08/2015.
 */
public class UploadActivity extends AsyncTask<Void, Integer, String> {
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private String filePath = null;
    long totalSize = 0;

    @Override
    protected String doInBackground(Void... params) {
        return uploadFile();
    }

    @SuppressWarnings("deprecation")
    private String uploadFile() {
        String responseString;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });

            File sourceFile = new File(filePath);

            // Adding file data to http body
            entity.addPart("image", new FileBody(sourceFile));

            // Extra parameters if you want to pass to server
            entity.addPart("website",
                    new StringBody("www.androidhive.info"));
            entity.addPart("email", new StringBody("abc@gmail.com"));

            totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }

        } catch (IOException e) {
            responseString = e.toString();
        }
        return responseString;
    }
    @Override
    protected void onPostExecute(String result) {
        Log.e(TAG, "Response from server: " + result);

        // showing the server response in an alert dialog
        //showAlert(result);

        super.onPostExecute(result);
    }

//    private void showAlert(String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.);
//        builder.setMessage(message).setTitle("Response from Servers")
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // do nothing
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }


}
/*Done Step 10 at http://www.androidhive.info/2014/12/android-uploading-camera-image-video-to-server-with-progress-bar/*/