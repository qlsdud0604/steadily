package com.Steadily.exerciseapplication.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Steadily.exerciseapplication.R;

import static com.Steadily.exerciseapplication.page.BMI_Activity.userBMI;

/**
 * 사용자의 키를 제어하는 클래스
 */
class Height {
    /**
     * 사용자로 부터 입력받은 키를 반환하는 메소드
     */
    static public float getHeight(EditText putHeight) {
        return Float.parseFloat(putHeight.getText().toString());
    }
}

/**
 * 사용자의 몸무게를 제어하는 클래스
 */
class Weight {
    /**
     * 사용자로 부터 입력받은 몸무게를 반환하는 메소드
     */
    static public float getWeight(EditText putWeight) {
        return Float.parseFloat(putWeight.getText().toString());
    }
}

/**
 * 사용자의 BMI 지수를 제어하는 클래스
 */
class BMI {
    /**
     * 사용자의 BMI 지수를 계산하는 메소드
     */
    static public void getBMI(EditText putHeight, EditText putWeight, TextView getBMI) {
        float height, weight, bmi;
        height = Height.getHeight(putHeight) / 100;
        weight = Weight.getWeight(putWeight);
        bmi = weight / (height * height);

        userBMI = bmi;

        getBMI.setText(String.format(" BMI : %.2f", bmi));
    }
}

/**
 * 사용자의 몸상태를 제어하는 클래스
 */
class Condition {
    /**
     * 사용자의 몸상태를 계산하는 메소드
     */
    static public void getCondition(TextView getCondition, float userBMI) {
        if (userBMI <= 18.5) {
            getCondition.setText(R.string.underweight);
        } else if (userBMI > 18.5 && userBMI <= 23) {
            getCondition.setText(R.string.normal);
        } else if (userBMI > 23 && userBMI <= 25) {
            getCondition.setText(R.string.overweight);
        } else if (userBMI > 25 && userBMI <= 30) {
            getCondition.setText(R.string.obesity);
        } else if (userBMI > 30) {
            getCondition.setText(R.string.altitudeObesity);
        }
    }
}

public class BMI_Activity extends AppCompatActivity {
    private EditText putHeight, putWeight;
    private TextView getBMI, getCondition;
    private Button calculateBMI;
    static public float userBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        putHeight = (EditText) findViewById(R.id.putHeight);
        putWeight = (EditText) findViewById(R.id.putWeight);
        getBMI = (TextView) findViewById(R.id.getBMI);
        getCondition = (TextView) findViewById(R.id.getCondition);
        calculateBMI = (Button) findViewById(R.id.calculateBMI);

        getBMI.setEnabled(false);
        getCondition.setEnabled(false);

        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(putHeight.getText()) || TextUtils.isEmpty(putWeight.getText())) {
                    Toast.makeText(getApplicationContext(), R.string.informationMessage, Toast.LENGTH_SHORT).show();
                } else {
                    BMI.getBMI(putHeight, putWeight, getBMI);
                    Condition.getCondition(getCondition, userBMI);

                    putHeight.setText("");
                    putWeight.setText("");
                }
            }
        });

    }

    /**
     * 등록 메뉴로 이동하는 버튼
     */
    public void moveRegister(View view) {
        startActivity(new Intent(this, Register_Activity.class));
    }

    /**
     * 그래프 메뉴로 이동하는 버튼
     */
    public void moveGraph(View view) {
        startActivity(new Intent(this, Graph_Activity.class));
    }


    /**
     * BMI 메뉴로 이동하는 버튼
     */
    public void moveBMI(View view) {
        startActivity(new Intent(this, BMI_Activity.class));
    }

    /**
     * 설정 메뉴로 이동하는 버튼
     */
    public void moveSetting(View view) {
        startActivity(new Intent(this, Setting_Activity.class));
    }
}
