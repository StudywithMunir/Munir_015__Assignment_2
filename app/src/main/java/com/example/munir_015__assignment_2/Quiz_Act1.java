package com.example.munir_015__assignment_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Quiz_Act1 extends AppCompatActivity {

    public Button gotoQuestion;
    public RadioButton userAns1;
    public RadioButton userAns2;
    public int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity1);

        gotoQuestion = findViewById(R.id.cont);
        userAns1 = findViewById(R.id.firstRight);
        userAns2 = findViewById(R.id.secondRight);

        gotoQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(userAns1,userAns2);
            }
        });
    }

    private void checkAnswer(RadioButton answer1, RadioButton answer2){
        answer1 = findViewById(R.id.firstRight);
        answer2 = findViewById(R.id.secondRight);
        Intent call = new Intent(Quiz_Act1.this,Quiz_Act2.class);
        if (answer1.isChecked() && answer2.isChecked()){
            call.putExtra("score",score+2);
        }
        else if (answer1.isChecked() || answer2.isChecked()){
            call.putExtra("score",score+1);
        }
        else {
            call.putExtra("score",score+0);
        }
        startActivity(call);
    }
}
