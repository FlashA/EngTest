package ru.comrades.engtest.Activities;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ru.comrades.engtest.DBDataHelper;
import ru.comrades.engtest.R;

public class TestActivity extends AppCompatActivity {

    private Button button_previous;
    private Button button_skip;
    private Button button_next;

    private TextView textView_question;

    private RadioGroup radioGroup;

    private DBDataHelper DBHelper;

    private int counterQuestions = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        DBHelper = new DBDataHelper(this);

        button_previous = (Button) findViewById(R.id.button_previous);
        button_skip = (Button) findViewById(R.id.button_skip);
        button_next = (Button) findViewById(R.id.button_next);

        button_previous.setVisibility(View.INVISIBLE);

        textView_question = (TextView) findViewById(R.id.textView_question);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        Log.d("my_app", "onCreate");

        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterQuestions == 25) button_next.setText("Следующий вопрос");
                if (counterQuestions != 1) {
                    counterQuestions--;
                    changeQuestion();
                    if (counterQuestions == 1) button_previous.setVisibility(View.INVISIBLE);
                }
            }
        });
        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    counterQuestions++;
                    changeQuestion();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterQuestions==24) button_next.setText("Завершить тест");
                if(counterQuestions!=25) {
                    counterQuestions++;
                    changeQuestion();
                }
                if(counterQuestions==25) {
                    Intent intent = new Intent(TestActivity.this, AnswersActivity.class);
                    startActivity(intent);
                }
                if (counterQuestions>=2) button_previous.setVisibility(View.VISIBLE);
            }
        });
    }

    private void changeQuestion() {
        try {
            textView_question.setText(counterQuestions + ". " +DBHelper.getQuestion(counterQuestions));
            radioGroup.removeAllViews();
            for(int i=0; i<DBHelper.getVariantsOfTheAnswer(counterQuestions).size(); i++) {
                final RadioButton radioButton = new RadioButton(getApplicationContext());
                radioButton.setText(DBHelper.getVariantsOfTheAnswer(counterQuestions).get(i));
                radioButton.setId(i);
                if(DBHelper.getSizeTempTable()!= 0 && DBHelper.getUserAnswerBoolean(counterQuestions)) {
                    if (i==DBHelper.getUserAnswer(counterQuestions)){
                        radioButton.setChecked(true);
                    }
                }
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBHelper.addUserAnswer(counterQuestions, radioButton.getId());
                        Log.d("my_app", "counterQuestions=" + counterQuestions + " : " + DBHelper.getUserAnswer(counterQuestions));
                    }
                });
                radioGroup.addView(radioButton);
            }
        } catch (CursorIndexOutOfBoundsException e) {
            Log.d("my_app", e.toString());
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counterQuestions = savedInstanceState.getInt("counterQuestions");;
        Log.d("my_app", "onRestoreInstanceState");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counterQuestions", counterQuestions);
        Log.d("my_app", "onSaveInstanceState");
    }

    protected void onResume() {
        super.onResume();
        changeQuestion();
        if(counterQuestions>1) button_previous.setVisibility(View.VISIBLE);
        if(counterQuestions==25) button_next.setText("Завершить тест");
        Log.d("my_app", "onResume");
    }
}