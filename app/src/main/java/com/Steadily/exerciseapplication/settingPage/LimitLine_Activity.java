package com.Steadily.exerciseapplication.settingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Steadily.exerciseapplication.R;
import com.Steadily.exerciseapplication.page.Setting_Activity;

public class LimitLine_Activity extends AppCompatActivity {
    private EditText putGoal;
    private Button saveGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limitline);

        putGoal = (EditText) findViewById(R.id.putGoal);
        saveGoal = (Button) findViewById(R.id.saveGoal);

        final SharedPreferences goalValue = getSharedPreferences("goalValue", MODE_WORLD_READABLE);

        final Intent backIntent = new Intent(this, Setting_Activity.class);

        /** 'saveGoal' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        saveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(putGoal.getText())) {
                    Toast.makeText(getApplicationContext(), R.string.informationMessage, Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = goalValue.edit();
                    editor.putInt("goalValue", Integer.parseInt(putGoal.getText().toString()));
                    editor.commit();

                    putGoal.setText("");

                    startActivity(backIntent);
                }
            }
        });
    }
}
