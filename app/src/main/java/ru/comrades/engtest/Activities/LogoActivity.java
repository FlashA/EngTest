package ru.comrades.engtest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.comrades.engtest.DBDataHelper;
import ru.comrades.engtest.R;

public class LogoActivity extends AppCompatActivity {

    private Button button_start;

    private DBDataHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        button_start = (Button) findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper = new DBDataHelper(getApplicationContext());
                DBHelper.clearTableTemp();
                Intent intent = new Intent(LogoActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

    }
}
