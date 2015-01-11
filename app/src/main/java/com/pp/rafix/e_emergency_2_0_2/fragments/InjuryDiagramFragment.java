package com.pp.rafix.e_emergency_2_0_2.fragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pp.rafix.e_emergency_2_0_2.R;
import com.pp.rafix.e_emergency_2_0_2.models.InjuryDiagramModel;
import com.pp.rafix.e_emergency_2_0_2.models.PatientModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class InjuryDiagramFragment extends Fragment implements View.OnTouchListener {

    @InjectView(R.id.imageViewInjuryDiagram)ImageView injuryDiagram;
    @InjectView(R.id.injuryTypeSpinner)Spinner injuryTypeSpinner;

    Bitmap bitmap;
    ArrayList<String> injuryNameShortcuts;


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

        injuryTypeSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity(),R.array.injury_diagram_array, android.R.layout.simple_spinner_item));
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.injury_diagram);
        injuryNameShortcuts = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.injury_diagram_shortcuts_array)));
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

        float x =  bitmap.getWidth() / (float) injuryDiagram.getWidth() * event.getX();
        float y =  bitmap.getHeight() / (float) injuryDiagram.getHeight()  * event.getY();

        InjuryDiagramModel newInjury = new InjuryDiagramModel();
        newInjury.id = injuryTypeSpinner.getSelectedItemPosition();
        newInjury.name = injuryTypeSpinner.getSelectedItem().toString();
        newInjury.shortcut = injuryNameShortcuts.get( injuryTypeSpinner.getSelectedItemPosition());
        newInjury.x = x;
        newInjury.y = y;
        newInjury.imageWidth = bitmap.getWidth();
        newInjury.imageHeight = bitmap.getHeight();

        PatientModel.getInstance().addDiagramInjury(newInjury);

        drawInjuries();

        return false;
    }

    private void drawInjuries(){

        Paint myCirclePaint = new Paint();
        myCirclePaint.setAntiAlias(true); // smooths out the edges of what is being drawn
        myCirclePaint.setColor(Color.RED); // set color
        myCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE); // set style
        myCirclePaint.setStrokeWidth(4.5f); // set stroke

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(20);

        Bitmap bitmap2 = bitmap.copy(bitmap.getConfig(), true);     //lets bmp to be mutable
        Canvas tempCanvas = new Canvas(bitmap2);

        for (InjuryDiagramModel injury :PatientModel.getInstance().getDiagramInjuries()){

            tempCanvas.drawCircle(injury.x, injury.y, 5, myCirclePaint);
            tempCanvas.drawText(injury.shortcut, injury.x + 10, injury.y, textPaint);
        }

        injuryDiagram.setImageBitmap(bitmap2);
    }

    @OnClick(R.id.buttonBack)
    public void onBackButtonClick(){

        PatientModel.getInstance().removeLastDiagramInjury();
        drawInjuries();
    }
}
