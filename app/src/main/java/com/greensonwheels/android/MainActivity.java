package com.greensonwheels.android;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

//import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener  {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private static GoogleApiClient mGoogleApiClient;

    public static String UserName;
    public static String UserEmail;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle(getResources().getString(R.string.app_name));

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        Log.d("API", result.toString());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {

                if (acct.getEmail().contains("@earthouseschool.org")) {
                    UserName = acct.getDisplayName();
                    UserEmail = acct.getEmail();
                    Intent orderIntent = new Intent(this, OrderActivity.class);
                    mGoogleApiClient.disconnect();
                    startActivity(orderIntent);
                    Toast.makeText(MainActivity.this, acct.getEmail() + " signed in", Toast.LENGTH_SHORT).show();
                } else {
                    signOut();
                }
            }
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(MainActivity.this, "Sign in didn't work", Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(MainActivity.this, "Please use Earth House School Email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Sorry, connection failed. " + connectionResult);
    }
    //
    public static GoogleApiClient getClient(){
        return mGoogleApiClient;
    }
    public static String getUserName() {
        return UserName;
    }

    public static String getUserEmail() {
        return UserEmail;
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

/**
 * Sign OUt :
 * Plus.AccountApi.clearDefaultAccount(mGoogleApiClient)
 * mGoogleApiClient.disconnect();
 * mGoogleApiClient.connect();
 */


