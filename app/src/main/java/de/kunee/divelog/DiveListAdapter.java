package de.kunee.divelog;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.joda.time.LocalDate;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.kunee.divelog.data.DiveLogContract;

public abstract class DiveListAdapter extends RecyclerView.Adapter<DiveListAdapter.ViewHolder> implements DiveLogContract.Dives {

    private Context context;
    private CursorAdapter cursorAdapter;

    public DiveListAdapter(Context context, Cursor c, int flags) {
        this.context = context;
        this.cursorAdapter = new CursorAdapter(context, c, flags) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.dive_list_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();

                int diveNo = cursor.getInt(viewHolder.diveNoIndex);
                viewHolder.diveNo.setText("#" + diveNo);

                long diveDate = cursor.getLong(viewHolder.diveDateIndex);
                LocalDate localDate = new LocalDate(diveDate);
                viewHolder.diveDate.setText(localDate.toString());

                String location = cursor.getString(viewHolder.locationIndex);
                viewHolder.location.setText(location);

                final long id = cursor.getInt(viewHolder.diveIdIndex);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DiveListAdapter.this.onClick(id);
                    }
                });
            }
        };
    }

    protected abstract void onClick(long itemId);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Cursor cursor = cursorAdapter.getCursor();
        View view = cursorAdapter.newView(context, cursor, parent);
        ViewHolder viewHolder = new ViewHolder(view, cursor);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Cursor cursor = cursorAdapter.getCursor();
        cursor.moveToPosition(position);
        cursorAdapter.bindView(viewHolder.itemView, context, cursor);
    }

    @Override
    public int getItemCount() {
        return cursorAdapter.getCount();
    }

    public void swapCursor(Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.dive_list_item_dive_no)
        TextView diveNo;
        @Bind(R.id.dive_list_item_dive_date)
        TextView diveDate;
        @Bind(R.id.dive_list_item_location)
        TextView location;

        final int diveIdIndex;
        final int diveNoIndex;
        final int diveDateIndex;
        final int locationIndex;

        public ViewHolder(View view, Cursor cursor) {
            super(view);
            ButterKnife.bind(this, itemView);

            diveIdIndex = cursor.getColumnIndex(_ID);
            diveNoIndex = cursor.getColumnIndex(DIVE_NO);
            diveDateIndex = cursor.getColumnIndex(DIVE_DATE);
            locationIndex = cursor.getColumnIndex(LOCATION);
        }
    }
}
