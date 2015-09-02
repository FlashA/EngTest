package ru.comrades.engtest.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import ru.comrades.engtest.DBDataHelper;
import ru.comrades.engtest.ListAdapter;
import ru.comrades.engtest.R;

public class AnswersActivity extends AppCompatActivity {

    private ListAdapter mAdapter;

    private Button button_return;

    private DBDataHelper DBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        DBHelper = new DBDataHelper(this);

        final ListView g = (ListView) findViewById(R.id.listView);
        mAdapter = new ListAdapter(getApplicationContext(), DBHelper.getListOfUserAnswer());
        g.setAdapter(mAdapter);

        button_return = (Button) findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}