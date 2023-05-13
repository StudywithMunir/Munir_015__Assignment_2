package com.example.munir_015__assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CertificateActivity extends AppCompatActivity {

    public TextView score;
    public TextView percentage;
    public TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate);

        Intent call = getIntent();
        int obtainScore =call.getIntExtra("score",0);
        float obtainPercentage = obtainScore*100/10;

        score = findViewById(R.id.score);
        percentage = findViewById(R.id.percentage);
        status = findViewById(R.id.status);

        score.setText("Score: "+obtainScore);
        percentage.setText("Percentage: "+obtainPercentage);
        if (obtainPercentage>50)
            status.setText("Status: Pass");
        else
            status.setText("Status: Fail");
    }
}