package de.kunee.divelog;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.joda.time.LocalDate;

import de.kunee.divelog.data.DiveLogContract;

public class DiveListAdapter extends CursorAdapter implements DiveLogContract.Dives {

    public DiveListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.dive_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, cursor);
        view.setTag(viewHolder);
        return view;
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
    }

    @Override
    public long getItemId(int position) {
        Cursor cursor = (Cursor) getItem(position);
        if (cursor != null) {
            return cursor.getLong(cursor.getColumnIndex(DiveLogContract.Dives._ID));
        }
        return -1;
    }

    public static class ViewHolder {
        final TextView diveNo;
        final TextView diveDate;
        final TextView location;

        final int diveNoIndex;
        final int diveDateIndex;
        final int locationIndex;

        public ViewHolder(View parent, Cursor cursor) {
            diveNo = (TextView) parent.findViewById(R.id.dive_list_item_dive_no);
            diveDate = (TextView) parent.findViewById(R.id.dive_list_item_dive_date);
            location = (TextView) parent.findViewById(R.id.dive_list_item_location);

            diveNoIndex = cursor.getColumnIndex(DIVE_NO);
            diveDateIndex = cursor.getColumnIndex(DIVE_DATE);
            locationIndex = cursor.getColumnIndex(LOCATION);
        }
    }
}
