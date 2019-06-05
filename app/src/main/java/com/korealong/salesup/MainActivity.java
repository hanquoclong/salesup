package com.korealong.salesup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.korealong.salesup.activities.HomeActivity;
import com.korealong.salesup.activities.LoginActivity;
import com.korealong.salesup.utils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkUserLogin();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void checkUserLogin() {
        if (PreferenceUtils.getEmail(this) == null) {
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivity(intentLogin);
            finish();
        } else {
            Intent intentHome = new Intent(this, HomeActivity.class);
            startActivity(intentHome);
            finish();
        }
    }
}
