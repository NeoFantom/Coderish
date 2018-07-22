package com.example.huiqixue.coderish;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class UnicodeActivity extends AppCompatActivity {

    private EditText editText;

    private StringAdapter unicodeAdapter;

    private CharSequence rawCsEditText = "";

    private int unicodeFormat = 10;

    private static final int[] formats = new int[]{2, 8, 10, 16};

    private static final String[] formatsString = new String[]{
            "Binary with 0 or 1", "Octal with 0-7", "Decimal with 0-9", "Hexadecimal with 0-F"};

    private void refreshUnicodeAdapter() {
        List<String> stringList = unicodeAdapter.getStringList();
        stringList.clear();
        for (int i = 0; i < rawCsEditText.length(); i++) {
            stringList.add(formattedUnicodeString(rawCsEditText.charAt(i), unicodeFormat));
        }
        unicodeAdapter.notifyDataSetChanged();
    }

    /**
     * @param format takes value among 2, 8, 10, 16
     * @param c      a character of unicode s
     * @return "(format){@code c}: formatted_unicode"
     */
    private String formattedUnicodeString(char c, int format) {
        String s = charToUnicodeString(c, format);
        int interval = 0;
        String prefix;
        if (format == 10)
            return "(Decimal) '" + c + "': " + s;
        else if (format == 2) {
            prefix = "(Binary) '" + c + "': ";
            interval = 8;
        } else if (format == 16) {
            prefix = "(Hexa) '" + c + "': ";
            interval = 2;
        } else if (format == 8)
            return "(Octal) '" + c + "': " + s;
        else
            return "!Program Error!";

        StringBuilder stringBuilder = new StringBuilder(s);
        // Fill with zeros
        while ((stringBuilder.length() % interval) != 0) {
            stringBuilder.insert(0, '0');
        }
        // Insert white space
        for (int i = stringBuilder.length() - interval; i > 0; i -= interval) {
            stringBuilder.insert(i, ' ');
        }
        return prefix + stringBuilder.toString();
    }

    private String charToUnicodeString(char c, int format) {

        if (format == 2)
            return Long.toBinaryString((long) c);
        if (format == 8)
            return Long.toOctalString((long) c);
        if (format == 10)
            return "" + ((long) c);
        if (format == 16)
            return Long.toHexString((long) c).toUpperCase();

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_unicode);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView unicodeRecyclerView = findViewById(R.id.recycler_view);
        unicodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        unicodeAdapter = new StringAdapter();
        unicodeRecyclerView.setAdapter(unicodeAdapter);

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
                rawCsEditText = editText.getText();
                refreshUnicodeAdapter();
            }
        });

        Button clearButton = findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.unicode_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_format_settings:
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        formatsString);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                unicodeFormat = formats[which];
                                dialog.dismiss();
                                refreshUnicodeAdapter();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }
}
