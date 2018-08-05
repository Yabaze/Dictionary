package com.thomas.mirakle.dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    DatabaseAccess databaseAccess;
    SearchView search;
    List<String> word;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        search=(SearchView) findViewById(R.id.search);

        databaseAccess=new DatabaseAccess(this);

        word = databaseAccess.getAllWords();
        listView.setClickable(true);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, word);
        listView.setAdapter(adapter);

        search.setOnQueryTextListener(new QueryTextListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: "+listView.getItemAtPosition(position));
                List<Dict> details;
                details=databaseAccess.getWordDetail(listView.getItemAtPosition(position).toString());
                CustomAdaptor adapter1 = new CustomAdaptor(MainActivity.this, details);
                listView.setAdapter(adapter1);
                listView.setClickable(false);
            }
        });
    }
    public class QueryTextListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            word.clear();
            word = databaseAccess.getSingleWords(query);
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, word);
            listView.setClickable(true);
            listView.setAdapter(adapter);
            return false;
        }
        @Override
        public boolean onQueryTextChange(String newText) {
            word.clear();
            word = databaseAccess.getCurrentWords(newText);
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, word);
            listView.setClickable(true);
            listView.setAdapter(adapter);
            return false;
        }
    }
}
