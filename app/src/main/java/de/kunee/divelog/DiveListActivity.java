package de.kunee.divelog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DiveListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dive_list_activity);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.dive_list_container, new DiveListFragment())
                    .commit();
        }
    }

}
