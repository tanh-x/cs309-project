package com.kewargs.cs309.audit;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.core.models.in.DegreeAuditDeserializable;

import java.util.stream.Collectors;

public class DegreePlannerActivity extends AbstractActivity {
    public DegreePlannerActivity() { super(R.layout.activity_degree_planner); }

    private String serializedParsedAudit;
    private DegreeAuditDeserializable audit;

    private ImageButton backButton;

    private TextView curriculumText;
    private TextView classifcationText;
    private TextView gpaText;
    private TextView entryText;
    private TextView graduationText;
    private TextView creditsCompletedText;
    private TextView creditsInpText;
    private TextView annotationsText;

    private TextView courseSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras == null) throw new IllegalStateException("No parsed audit for DegreePlannerActivity");
        serializedParsedAudit = extras.getString("audit");
        audit = DegreeAuditDeserializable.from(serializedParsedAudit);

        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();

        curriculumText.setText(audit.major());
        classifcationText.setText(audit.classification());
        gpaText.setText(String.valueOf(audit.gpa()));
        entryText.setText(audit.entryTerm().toString());
        graduationText.setText(audit.graduationTerm().toString());
        creditsCompletedText.setText(String.valueOf(audit.appliedCredits()));
        creditsInpText.setText(String.valueOf(audit.inProgressCredits()));
        annotationsText.setText(String.join("\n", audit.annotations()));
        courseSummary.setText(audit.courses().stream().map(c -> "[" + c.term().toString() + "] " + c.program() + " " + c.num() + ": " + c.grade()).collect(Collectors.joining("\n")));
    }

    @Override
    protected void collectElements() {
        backButton = findViewById(R.id.backButton);
        curriculumText = findViewById(R.id.curriculumText);
        classifcationText = findViewById(R.id.classifcationText);
        gpaText = findViewById(R.id.gpaText);
        entryText = findViewById(R.id.entryText);
        graduationText = findViewById(R.id.graduationText);
        creditsCompletedText = findViewById(R.id.creditsCompletedText);
        creditsInpText = findViewById(R.id.creditsInpText);
        annotationsText = findViewById(R.id.annotationsText);
        courseSummary = findViewById(R.id.courseSummary);
    }
}
