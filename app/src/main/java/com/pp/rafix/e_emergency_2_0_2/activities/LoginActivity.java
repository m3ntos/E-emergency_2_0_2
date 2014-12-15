package com.pp.rafix.e_emergency_2_0_2.activities;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;

import com.pp.rafix.e_emergency_2_0_2.R;
import com.pp.rafix.e_emergency_2_0_2.database.MyDatabaseOpenHelper;

public class LoginActivity extends ExpandableListActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db= MyDatabaseOpenHelper.getInstance(this).getWritableDatabase();

        createAdapter();

        setListViewOnClickListener();
    }

    private void setListViewOnClickListener() {
        getExpandableListView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                int groupCount = getExpandableListAdapter().getGroupCount();

                if(!expandableListView.isGroupExpanded(i)){
                    expandableListView.expandGroup(i);
                }else{
                    expandableListView.collapseGroup(i);
                }

                for (int j=0; j<groupCount; j++){
                    if(expandableListView.isGroupExpanded(j) && j!=i)
                        expandableListView.collapseGroup(j);

                }

                expandableListView.setItemChecked(i,true);

                return true;
            }
        });
    }

    private void createAdapter() {

        Cursor groupCursor = db.rawQuery("select * from crew", null);


        SimpleCursorTreeAdapter adapter = new SimpleCursorTreeAdapter(this, groupCursor,
                R.layout.layout,
                new String[]{"name"},
                new int[]{android.R.id.text1},
                android.R.layout.simple_list_item_1,
                new String[]{"memberName"},
                new int[]{android.R.id.text1}
        ) {
            @Override
            protected Cursor getChildrenCursor(Cursor groupCursor) {

                //get column by name
                String crewId = groupCursor.getString(groupCursor.getColumnIndex("_id"));

                return db.rawQuery("select e._id, (firstname||' '||lastname) as memberName " +
                        "from crew_employee c JOIN employee e ON (employee_id=e._id) " +
                        " where c.crew_id="+crewId, null);
            }
        };

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void loginOnClick(View v){
        Intent intent = new Intent(this,TabsActivity.class);
        startActivity(intent);
    }

    public void deleteOnClick(View v){

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Czy na pewno chcesz usunac zaloge?")
                .setTitle("Uwaga!");

        builder.setPositiveButton("Usun", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //db.delete("crew_employee");
            }
        });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
