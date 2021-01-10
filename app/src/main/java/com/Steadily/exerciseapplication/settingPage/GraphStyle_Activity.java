package com.Steadily.exerciseapplication.settingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.Steadily.exerciseapplication.R;
import com.Steadily.exerciseapplication.page.Setting_Activity;

public class GraphStyle_Activity extends AppCompatActivity {
    private RadioGroup styleGroup;

    private RadioButton HORIZONTAL_BEZIER;
    private RadioButton CUBIC_BEZIER;
    private RadioButton LINEAR;
    private RadioButton STEPPED;

    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_style);

        styleGroup = (RadioGroup) findViewById(R.id.styleGroup);

        HORIZONTAL_BEZIER = (RadioButton) findViewById(R.id.HORIZONTAL_BEZIER);
        CUBIC_BEZIER = (RadioButton) findViewById(R.id.CUBIC_BEZIER);
        LINEAR = (RadioButton) findViewById(R.id.LINEAR);
        STEPPED = (RadioButton) findViewById(R.id.STEPPED);

        backButton = (Button) findViewById(R.id.backButton);

        final SharedPreferences graphStyle = getSharedPreferences("graphStyle", 0);
        final SharedPreferences.Editor editor = graphStyle.edit();

        final int value = graphStyle.getInt("graphStyle", 1);

        if (value == 1) {
            HORIZONTAL_BEZIER.setChecked(true);
        } else if (value == 2) {
            CUBIC_BEZIER.setChecked(true);
        } else if (value == 3) {
            LINEAR.setChecked(true);
        } else if (value == 4) {
            STEPPED.setChecked(true);
        }
        final Intent backIntent = new Intent(this, Setting_Activity.class);

        /** 'backButton' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = styleGroup.getCheckedRadioButtonId();

                if ((RadioButton) findViewById(id) == HORIZONTAL_BEZIER) {
                    editor.putInt("graphStyle", 1);
                    editor.commit();

                    startActivity(backIntent);
                } else if ((RadioButton) findViewById(id) == CUBIC_BEZIER) {
                    editor.putInt("graphStyle", 2);
                    editor.commit();

                    startActivity(backIntent);
                } else if ((RadioButton) findViewById(id) == LINEAR) {
                    editor.putInt("graphStyle", 3);
                    editor.commit();

                    startActivity(backIntent);
                } else if ((RadioButton) findViewById(id) == STEPPED) {
                    editor.putInt("graphStyle", 4);
                    editor.commit();

                    startActivity(backIntent);
                }
            }
        });
    }
}