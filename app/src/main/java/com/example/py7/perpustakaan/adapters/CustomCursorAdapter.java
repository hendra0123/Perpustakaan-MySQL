package com.example.py7.perpustakaan.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.py7.perpustakaan.R;


public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_data, parent, false);
        ViewHolder holder = new ViewHolder();

        holder.listID = (TextView) view.findViewById(R.id.listID);
        holder.listJudul = (TextView) view.findViewById(R.id.listJudul);
        holder.listNama = (TextView) view.findViewById(R.id.listNama);
        holder.listPinjam = (TextView) view.findViewById(R.id.listTglPinjam);
        holder.listStatus = (TextView) view.findViewById(R.id.listStatus);

        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.listID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_id)));
        holder.listJudul.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_judul)));
        holder.listNama.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_nama)));
        holder.listPinjam.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_pinjam)) +
                " - " + cursor.getString(cursor.getColumnIndex(DBHelper.row_kembali)));
        holder.listStatus.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_status)));
    }

    static class ViewHolder {
        TextView listID;
        TextView listJudul;
        TextView listNama;
        TextView listPinjam;
        TextView listStatus;
    }
}
