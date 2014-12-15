package com.pp.rafix.e_emergency_2_0_2.fragments;



import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

import com.pp.rafix.e_emergency_2_0_2.R;
import com.pp.rafix.e_emergency_2_0_2.database.MyDatabaseOpenHelper;
import com.pp.rafix.e_emergency_2_0_2.models.PatientModel;


/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class PatientStateFragment extends Fragment {


    private ExpandableListView list;
    SimpleCursorTreeAdapter adapter;
    private SQLiteDatabase db;


    public PatientStateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_state, container, false);

        list = (ExpandableListView) view.findViewById(R.id.expandableListView);

        db=MyDatabaseOpenHelper.getInstance(getActivity()).getReadableDatabase();

        createAdapter();

        setListViewOnClickListener();

        return view;
    }




    private void createAdapter() {

        Cursor groupCursor = db.rawQuery("select * from injuryType", null);


        adapter = new SimpleCursorTreeAdapter(getActivity(), groupCursor,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{"name"},
                new int[]{android.R.id.text1},
                R.layout.my_simple_list_item_checked,
                new String[]{"name"},
                new int[]{android.R.id.text1}
        ) {
            @Override
            protected Cursor getChildrenCursor(Cursor groupCursor) {

                //get column by name
                String injuryTypeId = groupCursor.getString(groupCursor.getColumnIndex("_id"));

                return db.rawQuery("select * from injuryTreatment " +
                        " where injuryType_id=" + injuryTypeId, null);
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

                Cursor gc = adapter.getGroup(groupPosition);
                int groupId = gc.getInt(gc.getColumnIndex("_id"));
                String groupName = gc.getString(gc.getColumnIndex("name"));

                Cursor cc = adapter.getChild(groupPosition, childPosition);
                int childId = cc.getInt(cc.getColumnIndex("_id"));
                String childName = cc.getString(cc.getColumnIndex("name"));

                CheckedTextView checkBox = (CheckedTextView) super.getChildView(groupPosition, childPosition, isLastChild, null, parent);
                checkBox.setChecked(PatientModel.getInstance().isInjury(groupId, groupName, childId, childName));
                return checkBox;


            }
        };

        list.setAdapter(adapter);
    }

    private void setListViewOnClickListener() {
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {

                CheckedTextView checkBox = (CheckedTextView)view;
                checkBox.setChecked(!checkBox.isChecked());

                Cursor gc = adapter.getGroup(groupPosition);
                int groupId = gc.getInt(gc.getColumnIndex("_id"));
                String groupName = gc.getString(gc.getColumnIndex("name"));

                Cursor cc = adapter.getChild(groupPosition, childPosition);
                int childId = cc.getInt(cc.getColumnIndex("_id"));
                String childName = cc.getString(cc.getColumnIndex("name"));

                if(checkBox.isChecked()){
                    PatientModel.getInstance().addInjury(groupId, groupName, childId, childName);
                }else{
                    PatientModel.getInstance().removeInjury(groupId, groupName, childId, childName);
                }

                return false;
            }
        });
    }

}
