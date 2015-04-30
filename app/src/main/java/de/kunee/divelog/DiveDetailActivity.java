package de.kunee.divelog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class DiveDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dive_detail_activity);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.dive_detail_container, new DiveDetailFragment())
                    .commit();
        }
    }

}
