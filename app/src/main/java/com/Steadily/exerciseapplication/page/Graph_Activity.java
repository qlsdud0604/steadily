package com.Steadily.exerciseapplication.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.Steadily.exerciseapplication.R;
import com.Steadily.exerciseapplication.database.Database;
import com.Steadily.exerciseapplication.database.UserData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * 사용자의 그래프를 제어하는 클래스
 */
class Graph {
    /**
     * 그래프를 얻기위한 메소드
     */
    static public void getGraph(List<UserData> userDataList, LineChart getGraph, Context context, int styleValue) {

        ArrayList<Entry> values = new ArrayList<>();   // lineDataSet에 담겨질 데이터를 ArrayList 형태로 선언

        /** lineDataSet에 담겨질 사용자의 운동시간 정보를 "value"에 삽입 */
        for (int i = 0; i < userDataList.size(); i++) {
            int time = userDataList.get(i).getTime();

            values.add(new Entry(i, time));
        }

        if (values.isEmpty())   // "values"에 담겨진 데이터가 없을 경우 확대 및 축소 불가능
            getGraph.setScaleEnabled(false);
        else   // "values"에 담겨진 데이터가 있을 경우 확대 및 축소 가능
            getGraph.setScaleEnabled(true);

        /** lineDataSet에 대한 구체적인 설정 */
        LineDataSet lineDataSet = new LineDataSet(values, "Exercise Time");   // ArrayList인 "values"를 참조해 lineDataSet 선언
        lineDataSet.setColor(ContextCompat.getColor(context, R.color.easypink));   // 사용자에게 출력될 차트의 색상 설정
        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.easypink));   // 사용자에게 출력될 차트의 구분점 테두리 색상 설정
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(context, R.color.easypink));   // 사용자에게 출력될 차트의 구분점 구멍 색상 설정
        lineDataSet.setDrawFilled(true);
        Drawable graphFill = ContextCompat.getDrawable(context, R.drawable.graph_filled);   // 사용자에게 출력될 차트의 색상이 그라데이션으로 출력되도록 설정
        lineDataSet.setFillDrawable(graphFill);

        /** 정수형 매개변수인 "styleValue"의 값을 참조해 그래프 스타일을 변경할 수 있도록 설정 */
        if (styleValue == 1) {
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        } else if (styleValue == 2) {
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else if (styleValue == 3) {
            lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        } else if (styleValue == 4) {
            lineDataSet.setMode(LineDataSet.Mode.STEPPED);
        }

        /** lineData에 대한 구체적인 설정 */
        LineData lineData = new LineData();   // LineDataSet을 담는 그릇으로써 여러개의 LineDatSet이 삽입 가능
        lineData.addDataSet(lineDataSet);   // "lineData"에 위에서 선언한 "lineDataSet" 삽입
        lineData.setValueTextColor(ContextCompat.getColor(context, R.color.lightblack));   // 사용자에게 출력될 차트의 텍스트 색상 설정
        lineData.setValueTextSize(9);   // 사용자에게 출력될 차트의 텍스트 사이즈 설정

        /** 그래프의 x축에 대한 구체적인 설정 */
        XAxis xAxis = getGraph.getXAxis();   // x축에 해당하는 변수인 "xAxis" 선언
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);   // x축의 위치 설정
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.lightblack));   // x축에 표시될 텍스트 색상 설정
        xAxis.setGridColor(ContextCompat.getColor(context, R.color.superlightblack));   // x축의 색상 설정
        xAxis.setLabelCount(5);   // x축에 표시될 구간을 최대 5개로 설정

        final ArrayList<String> xLabelList = new ArrayList<String>();   // x축에 표시될 데이터들을 ArrayList 형태로 선언

        /** 데이터베이스에 저장된 사용자의 운동 일자 정보를 위에서 선언한 xLabelList에 추가 */
        for (int i = 0; i < userDataList.size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
            Date date = null;
            try {
                date = simpleDateFormat.parse(Float.toString(userDataList.get(i).getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd");
            String dateString = newFormat.format(date);

            xLabelList.add(dateString);
        }

        /** x축에 표시될 데이터(xLabelList)를 재가공하여 출력 */
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                if (xLabelList.size() == 1) {
                    if (value < 0)
                        return "";
                    else if (value == 1)
                        return "";
                    else
                        return xLabelList.get((int) value);
                } else
                    return xLabelList.get((int) value);
            }
        });

        /** 그래프의 y축에 대한 구체적인 설정 */
        YAxis yAxisLeft = getGraph.getAxisLeft();   // 좌측 y축에 해당하는 변수인 "yAxisLeft" 선언
        yAxisLeft.setTextColor(ContextCompat.getColor(context, R.color.lightblack));   // y축에 표시될 텍스트 색상 설정
        yAxisLeft.setGridColor(ContextCompat.getColor(context, R.color.lightblack));   // y축의 색상 설정
        yAxisLeft.setAxisMinimum(0f);   // y축에 표시될 최소 범위 설정
        yAxisLeft.setAxisMaximum(160f);   // y축에 표시될 최대 범위 설정
        yAxisLeft.setLabelCount(4);

        YAxis yAxisRight = getGraph.getAxisRight();   // 우측 y축에 해당하는 변수인 "yAxisRight" 선언
        yAxisRight.setDrawLabels(false);   // 우측 y축에 대한 미사용 설정
        yAxisRight.setDrawAxisLine(false);   // 우측 y축에 대한 미사용 설정
        yAxisRight.setDrawGridLines(false);   // 우측 y축에 대한 미사용 설정

        Legend legend = getGraph.getLegend();   // 레전드에 해당하는 변수인 "legend" 선언
        legend.setEnabled(false);   // 레전드에 대한 미사용 설정

        getGraph.setData(lineData);   // 위에서 설정한 그래프를 최종적으로 삽입
    }

    /**
     * 사용자가 설정한 목표선을 얻기위한 메소드
     */
    static public void getLimitLine(LineChart graph, boolean settingValue, int limit, Context context) {
        if (settingValue) {
            YAxis yAxisLeft = graph.getAxisLeft();

            LimitLine limitLine = new LimitLine(limit, "GOAL !!");
            limitLine.setTextColor(ContextCompat.getColor(context, R.color.hardpink));
            limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            limitLine.enableDashedLine(5, 3, 5);
            limitLine.setLineWidth(2);
            limitLine.setLineColor(ContextCompat.getColor(context, R.color.hardpink));
            yAxisLeft.addLimitLine(limitLine);
        }
    }

}


public class Graph_Activity extends AppCompatActivity {
    private LineChart getGraph;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getGraph = (LineChart) findViewById(R.id.graph);
        getGraph.setDragEnabled(true);
        getGraph.setScaleEnabled(true);

        db = Room.databaseBuilder(this, Database.class, "db").allowMainThreadQueries().build();

        SharedPreferences graphStyle = getSharedPreferences("graphStyle", 0);
        int styleValue = graphStyle.getInt("graphStyle", 1);
        SharedPreferences.Editor styleEditor = graphStyle.edit();
        styleEditor.commit();

        Graph.getGraph(db.userDataDao().getAll(), this.getGraph, getApplicationContext(), styleValue);


        SharedPreferences goalSetting = getSharedPreferences("goalSetting", 0);
        boolean settingValue = goalSetting.getBoolean("switchValue", false);
        SharedPreferences.Editor setEditor = goalSetting.edit();
        setEditor.commit();

        SharedPreferences goalValue = getSharedPreferences("goalValue", 0);
        int value = goalValue.getInt("goalValue", 60);
        SharedPreferences.Editor goalEditor = goalSetting.edit();
        goalEditor.commit();

        Graph.getLimitLine(getGraph, settingValue, value, getApplicationContext());

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
