package com.korealong.salesup.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.korealong.salesup.MainActivity;
import com.korealong.salesup.R;
import com.korealong.salesup.database.DatabaseHelper;
import com.korealong.salesup.helper.InputValidation;
import com.korealong.salesup.model.User;
import com.korealong.salesup.utils.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.korealong.salesup.utils.Constants.URL_LOGIN;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = LoginActivity.this;


    public TextInputLayout textInputLayoutEmail;
    public TextInputLayout textInputLayoutPassword;

    public  EditText edtEmail;
    public  EditText edtPassword;

    private Button appCompatButtonLogin;

    private TextView textViewLinkRegister;

    public InputValidation inputValidation;
    public DatabaseHelper databaseHelper;

    private RequestQueue requestQueue;
    private StringRequest request;

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();

        requestQueue = Volley.newRequestQueue(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = new User();
    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initViews() {

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        edtEmail = (EditText) findViewById(R.id.textInputEditTextEmail);
        edtPassword = (EditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (Button) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (TextView) findViewById(R.id.textViewLinkRegister);
        PreferenceUtils utils = new PreferenceUtils();
        if (utils.getEmail(this) != null ){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }else{

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(edtEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(edtEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(edtPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        saveUserToSQLite();

        getUserFromServer();
    }

    private void saveUserToSQLite() {
        if (!databaseHelper.checkUser(edtEmail.getText().toString().trim())) {

            user.setEmail(edtEmail.getText().toString().trim());
            user.setPassword(edtPassword.getText().toString().trim());

            databaseHelper.addUser(user);


        }
    }

    private void getUserFromServer() {
        request = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success")){

                        String email = edtEmail.getText().toString().trim();
                        String password = edtPassword.getText().toString().trim();

                        if (databaseHelper.checkUser(email, password)) {
                            PreferenceUtils.saveEmail(email, LoginActivity.this);
                            PreferenceUtils.savePassword(password, LoginActivity.this);
                            Intent accountsIntent = new Intent(activity, MainActivity.class);
                            accountsIntent.putExtra("EMAIL", email);
                            emptyInputEditText();
                            startActivity(accountsIntent);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(activity, "Error "+jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void emptyInputEditText() {
        edtEmail.setText(null);
        edtPassword.setText(null);
    }
}
