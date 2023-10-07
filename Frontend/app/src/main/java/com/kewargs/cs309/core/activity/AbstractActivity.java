package com.kewargs.cs309.core.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kewargs.cs309.core.manager.SessionManager;

public abstract class AbstractActivity extends AppCompatActivity {
    protected final SessionManager session = SessionManager.getInstance();
    private final int layoutId;

    public AbstractActivity(int layout) {
        this.layoutId = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        collectElements();
    }

    protected static void showToast(String content, Context context) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    protected static void showToast(String content, Context context, int toastLength) {
        Toast.makeText(context, content, toastLength).show();
    }

    protected void collectElements() { }
}
