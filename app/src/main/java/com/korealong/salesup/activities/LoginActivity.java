package com.korealong.salesup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.korealong.salesup.R;
import com.korealong.salesup.helper.InputValidation;
import com.korealong.salesup.helper.ServerHelper;
import com.korealong.salesup.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static com.korealong.salesup.utils.Constants.URL_LOGIN;

public class LoginActivity extends AppCompatActivity {

    public TextInputLayout textInputLayoutEmail;
    public TextInputLayout textInputLayoutPassword;

    public static EditText edtEmail;
    public static EditText edtPassword;
    private Button btnLogin;
    public InputValidation inputValidation;
    public ServerHelper serverHelper;
    public StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initObjects();
        initEvent();
    }

    private void initViews() {
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    private void initObjects() {
        inputValidation = new InputValidation(this);
        serverHelper = new ServerHelper();
    }

    private void initEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputValidation.isInputEditTextFilled(edtEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                    return;
                }
                if (!inputValidation.isInputEditTextEmail(edtEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                    return;
                }
                if (!inputValidation.isInputEditTextFilled(edtPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                    return;
                }
                getUserFromServer();
            }
        });
    }

    private void getUserFromServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String link = URL_LOGIN+edtEmail.getText().toString().trim()+"&password="+edtPassword.getText().toString().trim();
        request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() >2) {
                    String userName ="";
                    String userEmail = "";
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response);
                        for (int i = 0 ; i < response.length() ; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            userName = jsonObject.getString("name");
                            userEmail = jsonObject.getString("email");
                            PreferenceUtils.saveEmail(userEmail,LoginActivity.this);
                            PreferenceUtils.saveUserName(userName,LoginActivity.this);
                            Intent accountsIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            accountsIntent.putExtra("EMAIL", userEmail);
                            edtEmail.setText(null);
                            edtPassword.setText(null);
                            startActivity(accountsIntent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Thu lai", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("email",edtEmail.getText().toString().trim());
                hashMap.put("password",edtPassword.getText().toString().trim());
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
}
