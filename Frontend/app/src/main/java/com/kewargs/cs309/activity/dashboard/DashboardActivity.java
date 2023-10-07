package com.kewargs.cs309.activity.dashboard;

import android.os.Bundle;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.core.activity.AbstractActivity;
import com.kewargs.cs309.utils.UniversalConstants;
import com.kewargs.cs309.utils.backend.RequestFactory;

public class DashboardActivity extends AbstractActivity {
    public DashboardActivity() { super(R.layout.activity_dashboard); }

    private TextView userInfoDump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session.addRequest(RequestFactory.GET()
            .url(UniversalConstants.USER_ENDPOINT + "id/" + session.userId())
            .onResponse(response -> {
                userInfoDump.setText(response);
            })
            .build()
        );
    }

    @Override
    protected void collectElements() {
        userInfoDump = findViewById(R.id.userInfoDump);
    }
}
