package de.kunee.divelog;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static de.kunee.divelog.data.DiveLogContract.Dives;

public class DiveListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int DIVE_LIST_LOADER_ID = 0;

    private DiveListAdapter adapter;

    public DiveListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dive_list_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.dive_list);
        adapter = new DiveListAdapter(getActivity(), null, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (id != -1) {
                    Intent intent = new Intent(getActivity(), DiveDetailActivity.class);
                    intent.setData(ContentUris.withAppendedId(Dives.CONTENT_URI, id));
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DIVE_LIST_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                Dives.CONTENT_URI,
                new String[]{
                        Dives._ID,
                        Dives.DIVE_NO,
                        Dives.DIVE_DATE,
                        Dives.LOCATION,
                },
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
