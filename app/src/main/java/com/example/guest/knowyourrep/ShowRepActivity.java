package com.example.guest.knowyourrep;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;


public class ShowRepActivity extends ListActivity {

    private ArrayList<Representative> mRepList;
    private RepAdapter mAdapter;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String zipCode = getIntent().getExtras().getString("zipCode");
        getRepInfo(zipCode, new Runnable() {
            @Override
            public void run() {
                setupContent();
            }
        });
        Log.d(TAG, "Main UI code is running"); //shows main code is running while weather code is running in background

    }

    private ArrayList<Representative> getRepInfo(String zipCode, final Runnable runnable) {

        String apiKey = "e762abad088b4ec68a011f7d0c26975a";
        String apiUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipCode + "&apikey=" + apiKey;

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();

                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            //if response is successful get representative data
                            mRepList = getRepsInList(jsonData);
                            runOnUiThread(runnable);

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }

            });
        } else {
            Toast.makeText(this, R.string.network_unavailable_toast, Toast.LENGTH_LONG).show();

        }
        return mRepList;
    }

    private ArrayList<Representative> getRepsInList(String jsonData) throws JSONException {
        //change responsibility for handling exception to whover calls getRepsInList

        JSONObject legislators = new JSONObject(jsonData); //get all data
        JSONArray results = legislators.getJSONArray("results"); //name of subgroup

        ArrayList repList = new ArrayList<Representative>();

        for(int i = 0; i < results.length(); i++)
        {

            JSONObject rep = results.getJSONObject(i);
            String repFirstName = rep.getString("first_name");
            Log.d(TAG, repFirstName + "");
            String repLastName = rep.getString("last_name");
            Log.d(TAG, repLastName + "");
            String repParty = rep.getString("party");
            Log.d(TAG, repParty + "");
            Representative representative = new Representative(repFirstName,repLastName,repParty);
            repList.add(representative);
        }

        return repList;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected() ){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void setupContent(){
        setListAdapter(mAdapter);
        mAdapter = new RepAdapter(this, mRepList);
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


}
