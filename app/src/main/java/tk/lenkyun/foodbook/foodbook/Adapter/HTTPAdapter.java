package tk.lenkyun.foodbook.foodbook.Adapter;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

//import org.json.JSONException;
//import org.json.JSONObject;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class HTTPAdapter implements ConnectionAdapter<HTTPRequest>{
    String mServer;
    public HTTPAdapter(String server){
        mServer = server;
    }

    public HTTPRequest createRequest(){
        return new HTTPRequest(this);
    }

    public Promise<ConnectionResult> postRequest(final HTTPRequest requestDetail){
        final Promise<ConnectionResult> promise = new Promise<>();

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                Uri.Builder builder = Uri.parse(mServer).buildUpon();

                URL url = null;
                try {
                    for(String path : requestDetail.getServicePaths()){
                        builder.appendPath(path);
                    }

                    for(String param : requestDetail.getServiceParams()){
                        builder.appendPath(param);
                    }

                    if(requestDetail.getAuthenticationInfo() != null){
                        builder.appendQueryParameter("token", requestDetail.getAuthenticationInfo().getInfo());
                    }

                    if(!requestDetail.isSubmit()){
                        for(Map.Entry<String, Object> entry : requestDetail.getInputMap().entrySet()){
                            builder.appendQueryParameter(entry.getKey(), entry.getValue().toString());
                        }
                    }

                    url = new URL(builder.build().toString());

                } catch (MalformedURLException e) {
                    Log.e("HTTPAdapter", "Invalid server url format.");
                    return null;
                }

                try {
                    // Referral : http://stackoverflow.com/questions/29465996/how-to-get-json-object-using-httpurlconnection-instead-of-volley
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);

                    if(requestDetail.isSubmit()){
                        connection.setDoOutput(true);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("content-type", "application/json");
                        connection.connect();

                        String send = requestDetail.getInputJSON().toString();
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                        out.write(send);
                        out.flush();
                        out.close();
                    }else{
                        connection.setRequestMethod("GET");
                        connection.connect();
                    }


                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        result.append(line);
                    }

                    try {
                        promise.success("success", new HTTPResult(result.toString()));
                    } catch (JSONException e) {
                        promise.failed("Invalid JSON format.");
                    }
                } catch (IOException e) {
                    Log.e("HTTPAdapter", "Cant open connection to server.");
                    promise.failed("Cant open connection to server.");
                    return null;
                }

                return null;
            }
        }.execute();

        return promise;
    }
}
