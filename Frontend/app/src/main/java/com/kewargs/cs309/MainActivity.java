package com.kewargs.cs309;

import android.content.Intent;
import android.os.Bundle;

import com.kewargs.cs309.activity.auth.LoginActivity;
import com.kewargs.cs309.activity.dashboard.DashboardActivity;
import com.kewargs.cs309.core.activity.AbstractActivity;
import com.kewargs.cs309.core.manager.SessionManager;

public class MainActivity extends AbstractActivity {
    public MainActivity() { super(R.layout.activity_main); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager.initialize(this);

        if (session.isLoggedIn()) switchToDashboard();
        else switchToLogin();
    }

    private void switchToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void switchToDashboard() {
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
    }
}