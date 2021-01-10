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

public class AverageTime_Activity extends AppCompatActivity {
    private TextView checkAverageTime;
    private Button backButton;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_average_time);

        checkAverageTime = (TextView) findViewById(R.id.checkAverageTime);
        backButton = (Button) findViewById(R.id.backButton);

        db = Room.databaseBuilder(this, Database.class, "db").allowMainThreadQueries().build();

        List<UserData> userDataList = db.userDataDao().getAll();

        int allTime = 0;
        int count = 0;

        if (userDataList.isEmpty()) {
            checkAverageTime.setText(R.string.get0Minute);
        } else {

            for (UserData allUserData : userDataList) {
                allTime += allUserData.getTime();
                count++;
            }
            int averageTime = allTime / count;

            checkAverageTime.setText(getString(R.string.getMinutes) + averageTime + getString(R.string.minutes));
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