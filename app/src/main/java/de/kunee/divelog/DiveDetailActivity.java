package de.kunee.divelog;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import de.kunee.divelog.data.DiveLogContract;


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

    public static void start(Context context, long id) {
        Intent intent = new Intent(context, DiveDetailActivity.class);
        intent.setData(ContentUris.withAppendedId(DiveLogContract.Dives.CONTENT_URI, id));
        context.startActivity(intent);
    }

}
