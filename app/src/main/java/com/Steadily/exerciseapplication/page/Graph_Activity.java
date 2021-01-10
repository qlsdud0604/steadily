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

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < userDataList.size(); i++) {
            int time = userDataList.get(i).getTime();

            values.add(new Entry(i, time));
        }

        if (values.isEmpty())
            getGraph.setScaleEnabled(false);
        else
            getGraph.setScaleEnabled(true);

        LineDataSet lineDataSet = new LineDataSet(values, "Exercise Time");
        lineDataSet.setColor(ContextCompat.getColor(context, R.color.easypink));
        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.easypink));
        lineDataSet.setCircleHoleColor(ContextCompat.getColor(context, R.color.easypink));
        lineDataSet.setDrawFilled(true);
        Drawable graphFill = ContextCompat.getDrawable(context, R.drawable.graph_filled);
        lineDataSet.setFillDrawable(graphFill);

        if (styleValue == 1) {
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        } else if (styleValue == 2) {
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else if (styleValue == 3) {
            lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        } else if (styleValue == 4) {
            lineDataSet.setMode(LineDataSet.Mode.STEPPED);
        }


        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineData.setValueTextColor(ContextCompat.getColor(context, R.color.lightblack));
        lineData.setValueTextSize(9);
        lineData.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return "" + (int) value;
            }
        });

        XAxis xAxis = getGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ContextCompat.getColor(context, R.color.lightblack));
        xAxis.setGridColor(ContextCompat.getColor(context, R.color.superlightblack));
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(5);

        final ArrayList<String> xLabelList = new ArrayList<String>();

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

        YAxis yAxisLeft = getGraph.getAxisLeft();
        yAxisLeft.setTextColor(ContextCompat.getColor(context, R.color.lightblack));
        yAxisLeft.setGridColor(ContextCompat.getColor(context, R.color.lightblack));
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(160f);
        yAxisLeft.setLabelCount(4);

        YAxis yAxisRight = getGraph.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        Legend legend = getGraph.getLegend();
        legend.setEnabled(false);

        getGraph.setDescription(null);
        getGraph.setVisibleXRangeMaximum(5f);
        getGraph.setData(lineData);
        getGraph.invalidate();


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
