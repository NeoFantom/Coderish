package com.example.huiqixue.coderish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.List;

public class AlphabeticalOrderActivity extends AppCompatActivity {

    private EditText editText;

    private CharSequence csEditText;

    private StringAdapter adapter;

    public void refreshAdapter() {
        adapter.getStringList().clear();
        for (int i = 0; i < csEditText.length(); i++) {
            char c = csEditText.charAt(i);
            c = Character.toLowerCase(c);
            if ('a' <= c && c <= 'z')
                adapter.getStringList().add(c + " is at position: " + (c - 'a' + 1));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabetical_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                csEditText = s;
                refreshAdapter();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new StringAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
