package de.kunee.divelog;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.LocalDate;

import static de.kunee.divelog.data.DiveLogContract.Dives;

public class DiveDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int DIVE_DETAIL_LOADER_ID = 0;
    private static final String[] DETAIL_COLUMNS = {
            Dives._ID,
            Dives.DIVE_NO,
            Dives.DIVE_DATE,
            Dives.LOCATION,
            Dives.TIME_IN,
            Dives.DURATION_BOTTOM_TIME,
            Dives.DURATION_SAFETY_STOP,
            Dives.TANK_PRESSURE_BEFORE,
            Dives.TANK_PRESSURE_AFTER,
            Dives.DEPTH_MAX,
            Dives.TEMPERATURE_AIR,
            Dives.TEMPERATURE_SURFACE,
            Dives.TEMPERATURE_BOTTOM,
            Dives.VISIBILITY,
            Dives.WEIGHT,
            Dives.DIVE_SKIN,
            Dives.WET_SUIT,
            Dives.DRY_SUIT,
            Dives.HOODED_VEST,
            Dives.GLOVES,
            Dives.BOOTS,
            Dives.COMMENTS,
    };

    private TextView diveNoView;
    private TextView diveDateView;
    private TextView locationView;

    public DiveDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dive_detail_fragment, container, false);
        diveNoView = (TextView) rootView.findViewById(R.id.dive_detail_dive_no);
        diveDateView = (TextView) rootView.findViewById(R.id.dive_detail_dive_date);
        locationView = (TextView) rootView.findViewById(R.id.dive_detail_location);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DIVE_DETAIL_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.getData() != null) {
            return new CursorLoader(
                    getActivity(),
                    intent.getData(),
                    DETAIL_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }

        int diveNo = cursor.getInt(cursor.getColumnIndex(Dives.DIVE_NO));
        diveNoView.setText("#" + diveNo);

        long diveDate = cursor.getLong(cursor.getColumnIndex(Dives.DIVE_DATE));
        LocalDate localDate = new LocalDate(diveDate);
        diveDateView.setText(localDate.toString());

        String location = cursor.getString(cursor.getColumnIndex(Dives.LOCATION));
        locationView.setText(location);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
