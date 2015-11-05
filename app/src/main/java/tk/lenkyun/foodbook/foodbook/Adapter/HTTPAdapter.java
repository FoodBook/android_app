package tk.lenkyun.foodbook.foodbook.Adapter;

import android.os.AsyncTask;
import android.util.Log;

//import org.json.JSONException;
//import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import tk.lenkyun.foodbook.foodbook.Promise.Promise;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class HTTPAdapter implements ConnectionAdapter<HTTPRequestDetail>{
    String mServer;
    public HTTPAdapter(String server){
        mServer = server;
    }

    public HTTPRequestDetail createRequest(){
        return new HTTPRequestDetail(this);
    }

    public Promise<JSONObject> postRequest(final HTTPRequestDetail requestDetail){
        final Promise<JSONObject> promise = new Promise<>();

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                URL url = null;
                try {
                    url = new URL(mServer + "/" + requestDetail.getService() + "/" + requestDetail.getParams());

                } catch (MalformedURLException e) {
                    Log.e("HTTPAdapter", "Invalid server url format.");
                    return null;
                }

                if(url != null){
                    try {
                        // Referral : http://stackoverflow.com/questions/29465996/how-to-get-json-object-using-httpurlconnection-instead-of-volley
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);

                        if(requestDetail.isSubmit()){
                            connection.setDoOutput(true);
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("content-type", "application/json");
                            connection.connect();

                            String send = requestDetail.getJSON().toString();
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
                            promise.success("success", new JSONObject(result.toString()));
                        } catch (JSONException e) {
                            promise.failed("Invalid JSON format.");
                        }
                    } catch (IOException e) {
                        Log.e("HTTPAdapter", "Cant open connection to server.");
                        promise.failed("Cant open connection to server.");
                        return null;
                    }
                }

                return null;
            }
        }.execute();

        return promise;
    }
}
