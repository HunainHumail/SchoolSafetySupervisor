package supervisorapp.schoolsafety.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button login;
    EditText username;
    EditText password;
    ProgressDialog pDialog;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.txtuserename);
        password = (EditText) findViewById(R.id.txtpassword);

        login = (Button) findViewById(R.id.buttonlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check text view for empty strings
                validate(username.getText().toString(),password.getText().toString());
            }
        });



    }

    public void validate(String nationalID, String Password)
    {
        final HashMap<String,String> param = new HashMap<>();
        param.put("UserName", nationalID);
        param.put("Password", Password);
        param.put("grant_type", "password");


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Logging In...");
        pDialog.setCancelable(false);
        pDialog.show();

        new  AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {

                String tempMessage = "";
                try
                {
                    SendLoginDataToServer(param);
                }
                catch (Exception e)
                {
                    pDialog.dismiss();
                    Log.v("login",e.getMessage().toString());
                }
                return tempMessage;
            }
        }.execute(null, null, null);

    }

    private void SendLoginDataToServer(final HashMap<String, String> param)
    {
        String url = Constant.LOGIN_API;
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject responseObject = new JSONObject(response);

                    if(responseObject.has("access_token"))
                    {
                        String accessToken = responseObject.getString("access_token");
                        String tokenType = responseObject.getString("token_type");//yh faltu line h iski need ni h
                        Log.v("LOGIN_API","Login Successful");
                        //new function to get user data
                        GetUserData(accessToken, username.getText().toString());
                    }


                }
                catch(Exception e)
                {
                    pDialog.dismiss();
                    Toast.makeText(Login.this, "Exception Occured", Toast.LENGTH_SHORT).show();
                    Log.v("LOGIN_API",e.getMessage());
                }

            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    pDialog.dismiss();
                    Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    Log.v("LOGIN_API", error.toString());
                }
            })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = param;
                return params;
            }
        };

        RequestQueue loginRequestQueue = Volley.newRequestQueue(this);
        loginRequestQueue.add(loginStringRequest);
    }


    private void GetUserData(String token, final String nationalID)
    {
        final HashMap<String, String> headers = new HashMap<>();

        headers.put("Authorization","bearer "+token);
        headers.put("Content-Type","application/x-www-form-urlencoded");

        new  AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {

                String tempMessage = "";
                try
                {
                    GetUserDataFromServer(headers, nationalID);
                }
                catch (Exception e)
                {
                    pDialog.dismiss();
                    Log.v("login",e.getMessage().toString());
                }
                return tempMessage;
            }
        }.execute(null, null, null);

    }

    private void GetUserDataFromServer(final HashMap<String, String> headers, String nationalID)
    {
        String url = Constant.GET_USER_DATA_API + nationalID;
        StringRequest getUserStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                pDialog.dismiss();
                try
                {
                    JSONObject responseObject = new JSONObject(response);
                    Toast.makeText(Login.this, responseObject.toString(), Toast.LENGTH_SHORT).show();


                    //getting user data in strings
                    JSONObject contentObject = responseObject.getJSONObject("content");
                    String userName = contentObject.getString("name");
                    String userEmail = contentObject.getString("email");
                    Log.v("GET_USER","Data Retrieved Successfully");

                    //save in shared prefs
                    prefs = getSharedPreferences(Constant.USER_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("user_name", userName);
                    editor.putString("user_email", userEmail);

                    editor.commit();


                    //change activity
                    Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(intent);
                    finish();

                }
                catch(Exception e)
                {
                    pDialog.dismiss();
                    Toast.makeText(Login.this, "Exception Occured in Get user", Toast.LENGTH_SHORT).show();
                    Log.v("GET_USER",e.toString());
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        pDialog.dismiss();
                        Toast.makeText(Login.this, "Cannot Fetch User Data", Toast.LENGTH_SHORT).show();
                        Log.v("GET_USER", error.toString());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = headers;
                return params;
            }
        };

        RequestQueue getUserRequestQueue = Volley.newRequestQueue(this);
        getUserRequestQueue.add(getUserStringRequest);

    }

    private void GetForumDataFromServer(final HashMap<String, String> headers, String nationalID)
    {
        String url = Constant.GET_FORUMS_API;


    }


}


// forumData.setForumId(contentObject.getString("forumId"));
//         forumData.setName(contentObject.getString("name"));
//         forumData.setStartDate(contentObject.getString("startDate"));
//         forumData.setEndDate(contentObject.getString("endDate"));
//         forumData.setTrainingBagId(contentObject.getString("trainingBagId"));
//         forumData.setLocationName(contentObject.getString("locationName"));
//         forumData.setLatitide(contentObject.getString("locationLatitude"));
//         forumData.setLongitude(contentObject.getString("locationLongitude"));
//         forumData.setLocationDescription(contentObject.getString("locationDescription"));
//         forumData.setSupervisorForum(contentObject.getString("supervisorForum"));
//         forumData.setTotalCount(contentObject.getString("totalCount"));
//
//         Log.v("HunainItem",forumData.getName());