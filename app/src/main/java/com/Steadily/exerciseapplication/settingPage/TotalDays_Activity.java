package com.Steadily.exerciseapplication.settingPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Steadily.exerciseapplication.R;
import com.Steadily.exerciseapplication.page.Setting_Activity;
import com.Steadily.exerciseapplication.database.Database;
import com.Steadily.exerciseapplication.database.UserData;

import java.util.List;

public class TotalDays_Activity extends AppCompatActivity {
    private TextView totalDays;
    private Button backButton;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_days);

        totalDays = (TextView) findViewById(R.id.checkTotalDays);
        backButton = (Button) findViewById(R.id.backButton);

        db = Room.databaseBuilder(this, Database.class, "db").allowMainThreadQueries().build();

        List<UserData> userDataList = db.userDataDao().getAll();

        if (userDataList.isEmpty())
            totalDays.setText(R.string.get0Day);
        else {
            totalDays.setText(getString(R.string.getDays) + userDataList.size() + getString(R.string.days));
        }
        final Intent backIntent = new Intent(this, Setting_Activity.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(backIntent);
            }
        });
    }
}
