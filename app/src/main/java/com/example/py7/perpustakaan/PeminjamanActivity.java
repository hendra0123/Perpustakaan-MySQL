package com.example.py7.perpustakaan;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.py7.perpustakaan.adapters.CustomCursorAdapter;
import com.example.py7.perpustakaan.adapters.DBHelper;

public class PeminjamanActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBHelper dbHelper;
    Context context;
    CustomCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PeminjamanActivity.this, AddActivity.class));
            }
        });

        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.list_pinjam);
        listView.setOnItemClickListener(this);

        setupListView();
    }

    private void setupListView() {
        Cursor cursor = dbHelper.allData();
        cursorAdapter = new CustomCursorAdapter(this, cursor, 0);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_peminjaman, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textViewId = (TextView) view.findViewById(R.id.listID);
        long itemId = Long.parseLong(textViewId.getText().toString());
        Cursor cursor = dbHelper.oneData(itemId);
        cursor.moveToFirst();

        Intent intent = new Intent(PeminjamanActivity.this, AddActivity.class);
        intent.putExtra(DBHelper.row_id, itemId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }
}
