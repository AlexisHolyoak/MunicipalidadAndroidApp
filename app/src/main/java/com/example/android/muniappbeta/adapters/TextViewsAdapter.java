package com.example.android.muniappbeta.adapters;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.muniappbeta.R;
import com.example.android.muniappbeta.model.Papeleta;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by peral on 3/04/2017.
 */

public class TextViewsAdapter {

    public void setTextViewsInContext(TextView[] textViews, Context context){
        for (int i=0;i<textViews.length;i++){
            textViews[i]=new TextView(context);
        }
    }
    public  void configurarTextViews(TextView[] textViews){
        for (int i=0;i<textViews.length;i++){
            textViews[i].setWidth(200);
            //  texto[i].setHeight(80);
            textViews[i].setPadding(20, 0, 0, 0);
            textViews[i].setGravity(Gravity.CENTER_VERTICAL);
            textViews[i].setBackgroundResource(R.drawable.table_rows);
            textViews[i].setAllCaps(true);
        }
    }
    public void addTextViewsToRow(TextView[] textViews,TableRow row){
        for (int i=0;i<textViews.length;i++){
            row.addView(textViews[i]);
        }
    }
}
