package com.app.drugcorner32.dc_template.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.app.drugcorner32.dc_template.Fragments.NotificationFragment;
import com.app.drugcorner32.dc_template.Interfaces.NotificationFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

public class NotificationActivity extends ActionBarActivity implements NotificationFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        replaceFragment(R.layout.fragment_notification,null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(int id,Object object){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.layout.fragment_notification:
                ft.add(R.id.container,NotificationFragment.newInstance(),NotificationFragment.TAG).commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }
}
