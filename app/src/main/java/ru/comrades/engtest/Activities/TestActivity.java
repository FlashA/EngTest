package ru.comrades.engtest.Activities;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private boolean checkFirstClick = true;

    private int counterQuestions = 1;

    private ArrayList<Integer> userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        userAnswers = new ArrayList<Integer>();

        DBHelper = new DBDataHelper(this);

        button_previous = (Button) findViewById(R.id.button_previous);
        button_skip = (Button) findViewById(R.id.button_skip);
        button_next = (Button) findViewById(R.id.button_next);

        textView_question = (TextView) findViewById(R.id.textView_question);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        changeQuestion();

        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterQuestions!=1) {
                    counterQuestions--;

                    changeQuestion();

                }
            }
        });
        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       //         Toast.makeText(getApplicationContext(), Integer.toString(DBHelper.getSize()), Toast.LENGTH_SHORT).show();
            //    if(counterQuestions!=25) {
           //         userAnswers.add(counterQuestions - 1, null);
                   // Log.d("my_app", counterQuestions + " : " + userAnswers.get(counterQuestions - 1));
                    counterQuestions++;
                    changeQuestion();
            //    }
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(counterQuestions!=25) {

                    counterQuestions++;
                    changeQuestion();
                }
            }
        });


    }

    private void changeQuestion() {

        try {
            textView_question.setText(DBHelper.getQuestion(counterQuestions));
            radioGroup.removeAllViews();
            for(int i=0; i<DBHelper.getVariantsOfTheAnswer(counterQuestions).size(); i++) {
                final RadioButton radioButton = new RadioButton(getApplicationContext());
                radioButton.setText(DBHelper.getVariantsOfTheAnswer(counterQuestions).get(i));
                radioButton.setId(i);

                if(DBHelper.getUserAnswerBoolean(counterQuestions)) {
                    if (i==DBHelper.getUserAnswer(counterQuestions))radioButton.setChecked(true); ;
                }

                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //    userAnswers.add(counterQuestions - 1, radioButton.getId());

                        DBHelper.addUserAnswer(counterQuestions, radioButton.getId());

                        Log.d("my_app", "counterQuestions=" +counterQuestions + " : "  + DBHelper.getUserAnswer(counterQuestions));
                    }
                });
                radioGroup.addView(radioButton);
            }
        } catch (CursorIndexOutOfBoundsException e) {
            Log.d("my_app", e.toString());
        }
    }
}
