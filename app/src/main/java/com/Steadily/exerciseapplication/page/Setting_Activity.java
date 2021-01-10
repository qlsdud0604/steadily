package com.Steadily.exerciseapplication.page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.Steadily.exerciseapplication.R;
import com.Steadily.exerciseapplication.database.Database;
import com.Steadily.exerciseapplication.settingPage.AverageTime_Activity;
import com.Steadily.exerciseapplication.settingPage.GraphStyle_Activity;
import com.Steadily.exerciseapplication.settingPage.LimitLine_Activity;
import com.Steadily.exerciseapplication.settingPage.TotalDays_Activity;

public class Setting_Activity extends AppCompatActivity {
    private Switch setLimitLine;
    private Button setGraphStyle;

    private Button checkTotalDays;
    private Button checkAverageTime;


    private Button deleteData;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setLimitLine = (Switch) findViewById(R.id.setLimitLine);
        setGraphStyle = (Button) findViewById(R.id.setGraphStyle);

        checkTotalDays = (Button) findViewById(R.id.checkTotalDays);
        checkAverageTime = (Button) findViewById(R.id.checkAverageTime);


        deleteData = (Button) findViewById(R.id.deleteData);

        SharedPreferences goalSetting = getSharedPreferences("goalSetting", MODE_WORLD_READABLE);
        boolean settingValue = goalSetting.getBoolean("switchValue", false);
        setLimitLine.setChecked(settingValue);

        db = Room.databaseBuilder(this, Database.class, "db").allowMainThreadQueries().build();


        /** 'setLimitLine' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        final Intent limitLineIntent = new Intent(this, LimitLine_Activity.class);
        setLimitLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startActivity(limitLineIntent);
                }
                SharedPreferences goalSetting = getSharedPreferences("goalSetting", 0);
                SharedPreferences.Editor editor = goalSetting.edit();
                editor.putBoolean("switchValue", isChecked);
                editor.commit();
            }
        });


        /** 'setGraphStyle' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        final Intent styleIntent = new Intent(this, GraphStyle_Activity.class);
        setGraphStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(styleIntent);
            }
        });


        /** 'checkTotalDays' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        final Intent daysIntent = new Intent(this, TotalDays_Activity.class);
        checkTotalDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(daysIntent);
            }
        });


        /** 'checkAverageTime' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        final Intent timeIntent = new Intent(this, AverageTime_Activity.class);
        checkAverageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(timeIntent);
            }
        });


        /** 'deleteData' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        final Intent backIntent = new Intent(this, Setting_Activity.class);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting_Activity.this, R.style.MyAlertDialogStyle);
                builder.setTitle(R.string.sureMessage);
                builder.setIcon(android.R.drawable.ic_menu_delete);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.userDataDao().deleteAll();
                        finish();
                        Toast.makeText(getApplicationContext(), R.string.deleteMessage, Toast.LENGTH_SHORT).show();
                        startActivity(backIntent);
                    }
                });
                builder.setNeutralButton(R.string.cancel, null);
                builder.create().show();
            }
        });
    }

    /**
     * 등록 메뉴로 이동하는 메소드
     */
    public void moveRegister(View view) {
        startActivity(new Intent(this, Register_Activity.class));
    }

    /**
     * 그래프 메뉴로 이동하는 메소드
     */
    public void moveGraph(View view) {
        startActivity(new Intent(this, Graph_Activity.class));
    }


    /**
     * BMI 메뉴로 이동하는 메소드
     */
    public void moveBMI(View view) {
        startActivity(new Intent(this, BMI_Activity.class));
    }

    /**
     * 설정 메뉴로 이동하는 메소드
     */
    public void moveSetting(View view) {
        startActivity(new Intent(this, Setting_Activity.class));
    }
}