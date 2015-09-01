package ru.comrades.engtest.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import ru.comrades.engtest.DBDataHelper;
import ru.comrades.engtest.ListAdapter;
import ru.comrades.engtest.R;

public class AnswersActivity extends AppCompatActivity {

    private ListAdapter mAdapter;

    private ArrayList<String> list;

    private DBDataHelper DBHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        DBHelper = new DBDataHelper(this);

        list = new ArrayList<String>(DBHelper.getListOfUserAnswer());

        final ListView g = (ListView) findViewById(R.id.listView);
        mAdapter = new ListAdapter(getApplicationContext(), DBHelper.getListOfUserAnswer());
        g.setAdapter(mAdapter);

    }

}