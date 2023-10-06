package com.kewargs.cs309.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {
    protected final SessionManager session = SessionManager.getInstance();
    private final int layoutId;

    public AbstractActivity(int layout) { this.layoutId = layout; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        collectElements();
    }

    protected void collectElements() { }
}
