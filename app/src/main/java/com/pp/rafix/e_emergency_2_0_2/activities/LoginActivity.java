package com.pp.rafix.e_emergency_2_0_2.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.pp.rafix.e_emergency_2_0_2.EemergencyAplication;
import com.pp.rafix.e_emergency_2_0_2.R;
import com.pp.rafix.e_emergency_2_0_2.models.PatientModel;
import com.pp.rafix.e_emergency_2_0_2.models.TeamMemberModel;
import com.pp.rafix.e_emergency_2_0_2.models.TeamModel;
import com.pp.rafix.e_emergency_2_0_2.rest.RestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RetrofitError;

public class LoginActivity extends ListActivity {

    @InjectView(R.id.progressBar) ProgressBar progressBar;

    ArrayList<TeamModel> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        startAsyncTask();
    }

    private void startAsyncTask(){

        progressBar.setVisibility(View.VISIBLE);

        //get sors and teams asynchronously
        new AsyncTask<Void, Void, RetrofitError>() {
            @Override
            protected RetrofitError doInBackground(Void... params) {

                RestService service = EemergencyAplication.getRestClient().getRestService();

                try {
                    teams = service.getTeams();
                    EemergencyAplication.setSorList(service.getSors());



                }catch (RetrofitError error){
                    return error;
                }
                return null;
            }

            @Override
            protected void onPostExecute(RetrofitError error) {

                progressBar.setVisibility(View.GONE);

                if(error == null){

                    setListAdapter();
                }else{
                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    Log.e(LoginActivity.class.getSimpleName(), error.toString());
                }
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        PatientModel.getInstance().setTeamId(teams.get(position).id);
        PatientModel.getInstance().setTeamName(teams.get(position).name);

        Intent intent = new Intent(this, TabsActivity.class);
        startActivity(intent);
    }

    private void setListAdapter(){

        // create the grid item mapping
        String[] from = new String[] {"name", "crew"};
        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

        // prepare the list of all records
        List<HashMap<String, String>> crews = new ArrayList<HashMap<String, String>>();

        for(TeamModel team : teams){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", team.name);

            String crew = "";
            for(TeamMemberModel teamMember: team.members){
                crew+= teamMember.firstName + " " + teamMember.lastName + ", ";
            }
            crew = crew.substring(0, crew.length()-2);

            map.put("crew", crew);
            crews.add(map);
        }

        // fill in the grid_item layout
        SimpleAdapter adapter = new SimpleAdapter(this, crews, android.R.layout.simple_list_item_2, from, to);
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

        switch (item.getItemId()){
            case R.id.action_refresh:
                startAsyncTask();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}
