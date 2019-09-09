package supervisorapp.schoolsafety.com;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Statistics extends Fragment {

    PieChart pieChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics,container,false);

        pieChart = (PieChart) view.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(android.R.color.white);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setMaxHighlightDistance(80f);



        Legend legend = pieChart.getLegend();

        legend.setEnabled(true);
        legend.setTextColor(R.color.appblue);
        legend.setTextSize(15);


        ArrayList<PieEntry>
                yValues = new ArrayList<>();
        yValues.add(new PieEntry(70f, "الطلاب الذين حضروا الملتقي"));

        yValues.add(new PieEntry(30f, "الطلاب الذين لم يحضروا الملتقي"));
//        ArrayList<PieEntry> xValues = new ArrayList<>();
//        xValues.add(new PieEntry(30f, "الطلاب الذين لم يحضروا الملتقي"));




        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");

       // PieDataSet dataSet1 = new PieDataSet(xValues, "Cities")
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(1f);
       // dataSet.setColors(new int[] {R.color.yelloworange,R.color.greenlight});
        dataSet.setColors(new int[] {Color.rgb(54,235,179), Color.rgb(248,214,32)});


        PieData data = new PieData((dataSet));
        data.setValueTextSize(5f);
        data.setValueTextColor(R.color.appblue);

        pieChart.setData(data);
        return view;


    }
}
