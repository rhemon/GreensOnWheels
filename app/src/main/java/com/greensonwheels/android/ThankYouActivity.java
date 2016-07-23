package com.greensonwheels.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class ThankYouActivity extends AppCompatActivity {

    public GoogleApiClient mGoogleApiClient = MainActivity.getClient();

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    public void orderMore(View v) {
        Intent i = new Intent(getApplicationContext(), OrderActivity.class);
        startActivity(i);
    }

    public void signOut(View v) {
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "You have signed out successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Click one more time sign out!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
}
