package com.kewargs.cs309;

import android.content.Intent;
import android.os.Bundle;

import com.kewargs.cs309.activity.auth.LoginActivity;
import com.kewargs.cs309.core.activity.AbstractActivity;

public class MainActivity extends AbstractActivity {
    public MainActivity() { super(R.layout.activity_main); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (session.getSessionToken() == null) switchToLogin();
        else switchToDashboard();
    }

    private void switchToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void switchToDashboard() {
        throw new UnsupportedOperationException();
    }
}