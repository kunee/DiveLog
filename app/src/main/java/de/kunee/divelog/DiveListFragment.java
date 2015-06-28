package de.kunee.divelog;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

import static de.kunee.divelog.data.DiveLogContract.Dives;

public class DiveListFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int DIVE_LIST_LOADER_ID = 0;

    @Bind(R.id.dive_list_root_layout)
    CoordinatorLayout rootLayout;

    @Bind(R.id.dive_list_toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.dive_list_toolbar)
    Toolbar toolbar;

    @Bind(R.id.dive_list_fab_button)
    FloatingActionButton fabButton;

    @Bind(R.id.dive_list)
    RecyclerView listView;

    @BindString(R.string.app_name)
    String appName;

    private DiveListAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dive_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initToolbar();
        initListView();
        initFabButton();
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
        listAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.swapCursor(null);
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(getTintedDrawable(R.drawable.ic_menu));
        toolbarLayout.setTitle(appName);
    }

    private void initListView() {
        listAdapter = new DiveListAdapter(getActivity(), null, 0) {

            @Override
            protected void onClick(long itemId) {
                DiveDetailActivity.start(getActivity(), itemId);
            }
        };
        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setHasFixedSize(true);
    }

    private void initFabButton() {
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(rootLayout, "Not yet implemented!", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
    }
}
