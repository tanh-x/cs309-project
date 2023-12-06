package com.kewargs.cs309.audit;

import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;

public class DegreePlannerActivity extends AbstractActivity {
    public DegreePlannerActivity() { super(R.layout.activity_degree_planner); }

    private TextView curriculumText;
    private TextView classifcationText;
    private TextView gpaText;
    private TextView entryText;
    private TextView graduationText;
    private TextView creditsCompletedText;
    private TextView creditsInpText;
    private TextView annotationsText;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void collectElements() {
        curriculumText = findViewById(R.id.curriculumText);
        classifcationText = findViewById(R.id.classifcationText);
        gpaText = findViewById(R.id.gpaText);
        entryText = findViewById(R.id.entryText);
        graduationText = findViewById(R.id.graduationText);
        creditsCompletedText = findViewById(R.id.creditsCompletedText);
        creditsInpText = findViewById(R.id.creditsInpText);
        annotationsText = findViewById(R.id.annotationsText);
    }
}
