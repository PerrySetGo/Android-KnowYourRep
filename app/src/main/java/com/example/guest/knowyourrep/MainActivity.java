package com.example.guest.knowyourrep;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private CurrentRep  mCurrentRep;

    //need field labels here
    @Bind(R.id.zipCodeInput) EditText mZipCodeInput;
    @Bind(R.id.submitButton) Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getRepInfo(mZipCodeInput.getText().toString());

                }
        });
    }
    private void getRepInfo(String zipCode) {
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
                            mCurrentRep = getCurrentDetails(jsonData);
                            Log.d(TAG, mCurrentRep + "");

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
    }
    private CurrentRep getCurrentDetails(String jsonData) throws JSONException {
        //change responsibility for handling exception to whover calls getCurrentDetails

        JSONObject legislators = new JSONObject(jsonData); //get all data
        JSONArray results = legislators.getJSONArray("results"); //name of subgroup

        for(int n = 0; n < results.length(); n++)
        {
            JSONObject rep = results.getJSONObject(n);
            Log.d(TAG, rep.getString("first_name"));
        }

        CurrentRep currentRep = new CurrentRep();
//        currentRep.setFirstName(results[0].getString("first_name"));
//        currentRep.setLastName(results.getString("last_name"));
//        currentRep.setParty(results.getString("party"));

        //Log.d(TAG,currentWeather.getFormattedTime());

        return currentRep;
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

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}
