package com.example.huiqixue.coderish;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class HexadecimalActivity extends AppCompatActivity {

    private static final String SUB_SCRIPT_16 = "\u208d\u2081\u2086\u208e";

    private TextView problemView;

    private boolean showingAnswer = false;

    private String[] problemAndAnswer = new String[2];

    private String[] getRandomProblem() {
        Random random = new Random(System.currentTimeMillis());
        if (random.nextInt(10) == 0) {
            // Problem type: converting two number systems
            int x;
            if (random.nextBoolean())
                x = random.nextInt(6) + 10;
            else
                x = (random.nextInt(15) + 1) * 16;
            problemAndAnswer[0] = Integer.toHexString(x).toUpperCase() + SUB_SCRIPT_16 + " =";
            problemAndAnswer[1] = " " + Integer.toString(x);
        } else {
            // Problem type: calculation
            int operator = random.nextInt(4);
            if (operator < 2) {
                // Multiplication
                int x = random.nextInt(15) + 1;
                int y = random.nextInt(15) + 1;
                problemAndAnswer[0] =
                        Integer.toHexString(x).toUpperCase() + " \u00d7 " + Integer.toHexString(y).toUpperCase() + " =";
                problemAndAnswer[1] = " " + Integer.toHexString(x * y).toUpperCase() + SUB_SCRIPT_16;
            } else if (operator == 2) {
                // Summation
                int x = random.nextInt(15) + 1;
                int y = random.nextInt(15) + 1;
                problemAndAnswer[0] =
                        Integer.toHexString(x).toUpperCase() + " + " + Integer.toHexString(y).toUpperCase() + " =";
                problemAndAnswer[1] = " " + Integer.toHexString(x + y) + SUB_SCRIPT_16;
            } else if (operator == 3) {
                // Subtraction
                int x = random.nextInt(15) + 1;
                int y = random.nextInt(15) + 1;
                problemAndAnswer[0] =
                        Integer.toHexString(x).toUpperCase() + " - " + Integer.toHexString(y).toUpperCase() + " =";
                int d = x - y;
                if (d < 0)
                    problemAndAnswer[1] = " -" + Integer.toHexString(-d).toUpperCase() + SUB_SCRIPT_16;
                else
                    problemAndAnswer[1] = " " + Integer.toHexString(x - y).toUpperCase() + SUB_SCRIPT_16;
            } else {
                // Error
            }
        }
        return problemAndAnswer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hexadecimal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager manager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        TableAdapter adapter = new TableAdapter(15, 15,
                new TableAdapter.DataCalculator() {
                    @Override
                    public String calculateData(int row, int column) {
                        return Integer.toHexString(column).toUpperCase()
                                + "\u00d7" // multiplication sign
                                + Integer.toHexString(row).toUpperCase()
                                + "="
                                + Integer.toHexString(row * column).toUpperCase();
                    }

                    @Override
                    public Typeface typeFaceAt(int row, int column) {
                        if (row == column)
                            return Typeface.DEFAULT_BOLD;
                        else
                            return Typeface.DEFAULT;
                    }
                });
        adapter.setHasHorizontalLines(false);
        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        problemView = findViewById(R.id.problem_text_view);
        problemView.setText(getRandomProblem()[0]);
        problemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showingAnswer) {
                    // Give another problem
                    problemView.setText(getRandomProblem()[0]);
                    showingAnswer = false;
                } else {
                    // Show answer
                    problemView.setText(problemAndAnswer[0] + problemAndAnswer[1]);
                    showingAnswer = true;
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            problemView.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            problemView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hexadecimal_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_problem_types:

                break;
        }
        return true;
    }
}
