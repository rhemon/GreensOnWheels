package com.greensonwheels.android;


import java.io.BufferedReader;
//import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import java.io.IOException;
//import java.util.List;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.json.*;
import org.json.JSONObject;
//import org.json.JSONTokener;

import android.content.Intent;
//import android.net.Uri;
//import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

//import com.ehscanteen.android.R;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;


/**
 * Created by rhemon on 2/20/16.
 */
public class OrderActivity extends AppCompatActivity {

    //    private ArrayList arrayOfClients;
//    private GoogleApiClient mGoogleApiClient;
//    private static final String TAG = "SignInActivity";
//    private static final int RC_SIGN_IN = 9001;
    public GoogleApiClient mGoogleApiClient = MainActivity.getClient();
    private String personName = MainActivity.getUserName();
    private String personEmail = MainActivity.getUserEmail();
    public static int price = 0;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    public static String orderData;
//    static int orderStatus = 0;
//    static int prevStatus = 0;
//    static boolean orderActive = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client2;

    private ArrayList<JSONObject> menuItems = new ArrayList<JSONObject>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        this.setTitle(getResources().getString(R.string.app_name));

        price = 0;
        final TextView priceView = (TextView) findViewById(R.id.totalPrice);
        TextView userId = (TextView) findViewById(R.id.userName);
        userId.setText("User: " + personName);
        new Thread(new Runnable() {
            @Override
            public void run() {

                getMenu();
                if (menuItems.size() == 0) {
                    mGoogleApiClient.connect();
                    mGoogleApiClient.connect();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                    if (mGoogleApiClient.isConnected()) {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Server error - Come back later", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(), "Server error - Come back later", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
                Log.d("API", menuItems.toString());
                final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

                tableLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        final ProgressBar tableProgressBar = (ProgressBar) findViewById(R.id.tableProgressBar);
                        final ScrollView scrollView = (ScrollView) findViewById(R.id.tableScroll);
//                        tableProgressBar.setProgress(0);
//                        int progressStatus = 0;
//                        int eachTimeProgress;
//                        if (menuItems.size() != 0 ) {
//                            eachTimeProgress = 100 / menuItems.size();
//                        } else {
//                            eachTimeProgress = 0;
//                        }

                        for (int j = 0; j < menuItems.size(); j++) {
                            final JSONObject itemObject = menuItems.get(j);
                            Log.d("API", "here");
                            try {
                                Log.d("API", itemObject.getString("item"));
                                TextView name = new TextView(OrderActivity.this);
                                name.setText(itemObject.get("item").toString());
                                TableRow.LayoutParams namelp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.35f);

                                namelp.gravity = Gravity.CENTER_VERTICAL;
                                name.setLayoutParams(namelp);
                                TextView stock = new TextView(OrderActivity.this);
                                stock.setText(itemObject.get("stock").toString());
                                TableRow.LayoutParams stocklp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.15f);
                                stocklp.gravity = Gravity.CENTER;
                                stock.setLayoutParams(stocklp);
                                TextView price = new TextView(OrderActivity.this);
                                price.setText(itemObject.get("price").toString());
                                TableRow.LayoutParams pricelp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.1f);
                                pricelp.gravity = Gravity.CENTER;
                                price.setLayoutParams(pricelp);

                                final TextView order = new TextView(OrderActivity.this);
                                order.setText(itemObject.get("order").toString());
                                TableRow.LayoutParams orderlp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.15f);
                                orderlp.gravity = Gravity.CENTER;
                                order.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                order.setLayoutParams(orderlp);

                                TableRow tableRow = new TableRow(OrderActivity.this);
                                ImageView add = new ImageView(OrderActivity.this);
//                                add.setText("+");
                                add.setImageResource(R.drawable.add);
//                                add.setLayoutParams(new LayoutParams(32, 32));
//                                add.setBackgroundResource(R.drawable.add);
                                TableRow.LayoutParams addlp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.125f);
                                addlp.gravity = Gravity.CENTER_VERTICAL;
                                add.setLayoutParams(addlp);
//                                LayoutParams addlp = add.getLayoutParams();
//                                addlp.height = WRAP_CONTENT;
//                                addlp.width = WRAP_CONTENT;
//                                add.setLayoutParams(addlp);
                                add.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            if (Integer.parseInt(itemObject.get("order").toString()) < Integer.parseInt(itemObject.get("stock").toString())) {
                                                int newValue = Integer.parseInt(itemObject.get("order").toString()) + 1;
                                                itemObject.remove("order");
                                                itemObject.put("order", newValue);
                                                Log.d("API", itemObject.toString());
                                                order.setText(String.valueOf(newValue));
                                                OrderActivity.price += Integer.parseInt(itemObject.get("price").toString());
                                                priceView.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        priceView.setText(String.valueOf(OrderActivity.price) + " TK");
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            Log.e("ADD", "error");
                                            Log.e("ADD", e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                ImageView sub = new ImageView(OrderActivity.this);
//                                sub.setText("-");
                                sub.setImageResource(R.drawable.sub);
//                                sub.setLayoutParams(new LayoutParams(32, 32));
//                                Log.d("API", String.valueOf(WRAP_CONTENT));
//                                LayoutParams sublp = sub.getLayoutParams();
//                                sublp.height = WRAP_CONTENT;
//                                sublp.width = WRAP_CONTENT;
//                                sub.setLayoutParams(sublp);
                                TableRow.LayoutParams sublp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.125f);
                                sublp.gravity = Gravity.CENTER_VERTICAL;
                                sub.setLayoutParams(sublp);

//                                sub.setWidth(65);
//                                sub.setHeight(50);
                                sub.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            if (Integer.parseInt(itemObject.get("order").toString()) > 0) {
                                                int newValue = Integer.parseInt(itemObject.get("order").toString()) - 1;
                                                itemObject.remove("order");
                                                itemObject.put("order", newValue);
                                                Log.d("API", itemObject.toString());
                                                order.setText(String.valueOf(newValue));
                                                OrderActivity.price -= Integer.parseInt(itemObject.get("price").toString());
                                                priceView.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        priceView.setText(String.valueOf(OrderActivity.price) + " TK");
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            Log.e("ADD", "error");
                                            Log.e("ADD", e.toString());
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                tableRow.addView(name);
                                tableRow.addView(stock);
                                tableRow.addView(price);
                                tableRow.addView(add);
                                tableRow.addView(order);
                                tableRow.addView(sub);
                                tableLayout.addView(tableRow);
//                                progressStatus += eachTimeProgress;
//                                tableProgressBar.setProgress(progressStatus);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("API", itemObject.toString());

                        }
                        tableProgressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }

                });

            }
        }).start();


    }


    private void getMenu() {

        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://greens-on-wheels.appspot.com/api/menu");
            HttpResponse response = client.execute(request);

            // Get the response
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));

            String line = rd.readLine();
            Log.d("API", line);
            String[] strings = line.split("'");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].contains("[") || strings[i].contains("]") || strings[i].contains(",")) {

                } else {
                    list.add(strings[i]);
                }
            }
            for (int i = 4; i < list.size(); i += 4) {
                JSONObject object = new JSONObject();
                if (list.get(i + 1).toString().equals("0")) {
                    continue;
                }
                object.put("item", list.get(i).toString());
                object.put("stock", list.get(i + 1).toString());
                object.put("price", list.get(i + 2).toString());
                object.put("id", list.get(i + 3).toString());
                object.put("order", "0");
                menuItems.add(object);
            }
            Log.d("API", menuItems.toString());
        } catch (Exception e) {
            Log.e("API", "error retrieving menu");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Server issues, please try later", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    // let the user sign out
    public void signOut(View v) {
//
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


    public void postOrder(View v) {
        findViewById(R.id.orderProgress).setVisibility(View.VISIBLE);
        findViewById(R.id.orderLayout).setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean allNotZero = true;
                OrderActivity.orderData = "{'name' : '"+ personName + "', 'email' : '"+ personEmail + "'";
                for (JSONObject itemObject : menuItems) {
                    try {
                        if (Integer.parseInt(itemObject.get("order").toString()) != 0) {
                            allNotZero = false;
                        }
                        OrderActivity.orderData += ", '" + itemObject.get("id").toString() + "' : '" + itemObject.get("order").toString() + "'";
                    } catch (Exception e) {
                        Log.e("API", "error in creating orderData string");
                    }
//                    orderStatus += 80/menuItems.size();
                }
                if (allNotZero) {
//                    orderActive = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "You need to at least order one item", Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent thisIntent = getIntent();
                    finish();
                    startActivity(thisIntent);
                } else {
                    try {
//                        orderStatus += 10;
                        OrderActivity.orderData += ", 'price' : '" + String.valueOf(OrderActivity.price) + "'}";
                        Log.d("API", OrderActivity.orderData);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost("http://greens-on-wheels.appspot.com/api/order");
                        //HttpPost post = new HttpPost("http://test.localhost");
                        post.setEntity(new StringEntity(OrderActivity.orderData));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                        String line = rd.readLine();
//                        orderStatus += 10;
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {

                        }
                        if (line.equals("true")) {
//                            orderActive = false;
                            Intent thankYou = new Intent(getApplicationContext(), ThankYouActivity.class);
                            startActivity(thankYou);
                        } else {
//                            orderActive = false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Order wasn't place due to server issues", Toast.LENGTH_LONG).show();
                                }
                            });
                            Intent thisIntent = getIntent();
                            finish();
                            startActivity(thisIntent);
                        }

                    } catch (IOException e) {
//                        orderActive = false;
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Order wasn't place due to server issues", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        }).start();
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