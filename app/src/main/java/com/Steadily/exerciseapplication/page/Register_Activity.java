package com.Steadily.exerciseapplication.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.Steadily.exerciseapplication.R;
import com.Steadily.exerciseapplication.database.Database;
import com.Steadily.exerciseapplication.database.UserData;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 사용자의 운동 날짜를 제어하는 클래스
 */
class Date {
    /**
     * 사용자로 부터 운동 날짜를 입력받는 메소드
     */
    static void setDate(EditText putDate, Calendar calendar) {
        String stringFormat = "yyyy/MM/dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(stringFormat, Locale.KOREA);

        putDate.setText(dateFormat.format(calendar.getTime()));

    }

    /**
     * 입력된 운동 날짜를 데이터베이스에 저장하기 위해 적당한 형태로 바꾸기 위한 메소드
     */
    static public int getDate(EditText putDate) throws ParseException {
        String value = putDate.getText().toString();

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date date = originalFormat.parse(value);

        SimpleDateFormat newFormat = new SimpleDateFormat("yyMMdd");
        String dateValue = newFormat.format(date);

        return Integer.parseInt(dateValue);
    }
}

/**
 * 사용자의 운동 시간을 제어하는 클래스
 */
class Time {
    /**
     * 사용자로부터 입력받은 운동 시간을 반환하는 메소드
     */
    static public int getTime(EditText putTime) {
        return Integer.parseInt(putTime.getText().toString());
    }
}

public class Register_Activity extends AppCompatActivity {
    private EditText putDate;
    private Button setDate;
    private EditText putTime;
    private Button saveData;

    private Database db;

    Calendar calendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date.setDate(putDate, calendar);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        putDate = (EditText) findViewById(R.id.putDate);
        setDate = (Button) findViewById(R.id.setDate);
        putTime = (EditText) findViewById(R.id.putTime);
        saveData = (Button) findViewById(R.id.saveData);

        putDate.setEnabled(false);

        db = Room.databaseBuilder(this, Database.class, "db").allowMainThreadQueries().build();

        /** 'setDate' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register_Activity.this, R.style.MySpinnerDatePickerStyle, myDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "yes", datePickerDialog);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_NEUTRAL, "cancel", datePickerDialog);
                datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "", datePickerDialog);

                dividerColorChange(datePickerDialog.getDatePicker());
                datePickerDialog.show();

            }
        });

        /** 'saveData' 버튼을 눌렀을 때 수행되는 동작을 정의 */
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(putDate.getText()) || TextUtils.isEmpty(putTime.getText())) {
                    Toast.makeText(getApplicationContext(), R.string.informationMessage, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        db.userDataDao().put(new UserData(Date.getDate(putDate), Time.getTime(putTime)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), R.string.saveMessage, Toast.LENGTH_SHORT).show();

                    putDate.setText("");
                    putTime.setText("");

                }
            }
        });


    }

    public static void dividerColorChange(DatePicker datePicker) {
        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("day", "id", "android");
        int monthId = system.getIdentifier("month", "id", "android");
        int yearId = system.getIdentifier("year", "id", "android");

        NumberPicker dayPicker = (NumberPicker) datePicker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) datePicker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) datePicker.findViewById(yearId);

        setDividerColor(dayPicker);
        setDividerColor(monthPicker);
        setDividerColor(yearPicker);
    }

    /**
     * 디바이더의 색상을 변경하기 위한 메소드
     */
    private static void setDividerColor(NumberPicker picker) {
        if (picker == null)
            return;

        final int count = picker.getChildCount();
        for (int i = 0; i < count; i++) {
            try {
                Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(picker.getResources().getColor(R.color.hardpink));
                dividerField.set(picker, colorDrawable);
                picker.invalidate();
            } catch (Exception e) {
                Log.w("setDividerColor", e);
            }
        }
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
