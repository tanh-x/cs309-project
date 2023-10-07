package com.kewargs.cs309.core.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kewargs.cs309.core.manager.SessionManager;

public abstract class AbstractActivity extends AppCompatActivity {
    private final int layoutId;

    protected final SessionManager session = SessionManager.getInstance();

    public AbstractActivity(int layout) {
        this.layoutId = layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        collectElements();
    }

    protected void collectElements() { }
}
