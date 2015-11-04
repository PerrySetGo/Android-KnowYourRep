package com.example.guest.knowyourrep;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Representative> mRepList;

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

                String zipCode = mZipCodeInput.getText().toString();
                Intent intent = new Intent(MainActivity.this, ShowRepActivity.class);
                intent.putExtra("zipCode", zipCode);
                Log.d(TAG, zipCode);
                startActivity(intent);
            }
        });
    }
}
