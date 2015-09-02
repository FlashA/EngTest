package ru.comrades.engtest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ru.comrades.engtest.DBDataHelper;
import ru.comrades.engtest.ListItem;
import ru.comrades.engtest.R;

public class ResultActivity extends AppCompatActivity {

    private Button button_answers;
    private Button button_restart;

    private TextView textView_result;

    private DBDataHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView_result = (TextView) findViewById(R.id.textView_result);
        DBHelper = new DBDataHelper(this);
        textView_result.setText("Ваш результат: "+Integer.toString(countRightAnswers()) + " из 25");

        button_answers = (Button) findViewById(R.id.button_answers);
        button_answers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, AnswersActivity.class);
                startActivity(intent);
            }
        });

        button_restart = (Button) findViewById(R.id.button_restart);
        button_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ResultActivity.this, LogoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });


    }

    private int countRightAnswers() {
        int counter = 0;
        ArrayList<ListItem> list = new ArrayList<ListItem>(DBHelper.getListOfUserAnswer());
        for (ListItem item: list) {
            if (item.isRightAnswer()) counter++;
        }
        return counter;
    }
}
