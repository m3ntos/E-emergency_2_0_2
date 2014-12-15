package com.pp.rafix.e_emergency_2_0_2.fragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pp.rafix.e_emergency_2_0_2.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InjuryDiagramFragment extends Fragment implements View.OnTouchListener {

    @InjectView(R.id.imageViewInjuryDiagram)ImageView injuryDiagram;


    public InjuryDiagramFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_injury_diagram, container, false);
        ButterKnife.inject(this, view);

        injuryDiagram.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    /* imageViewInjuryDiagram on touch */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Paint myCirclePaint = new Paint();
        myCirclePaint.setAntiAlias(true); // smooths out the edges of what is being drawn
        myCirclePaint.setColor(Color.RED); // set color
        myCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE); // set style
        myCirclePaint.setStrokeWidth(4.5f); // set stroke

        Bitmap bitmap =((BitmapDrawable)injuryDiagram.getDrawable()).getBitmap();
        bitmap = bitmap.copy(bitmap.getConfig(), true);     //lets bmp to be mutable
        Canvas tempCanvas = new Canvas(bitmap);

        float x =  bitmap.getWidth() / (float) injuryDiagram.getWidth() * event.getX();
        float y =  bitmap.getHeight() / (float) injuryDiagram.getHeight()  * event.getY();

        tempCanvas.drawCircle(x, y, 10, myCirclePaint);
        injuryDiagram.setImageBitmap(bitmap);

        return false;
    }
}
