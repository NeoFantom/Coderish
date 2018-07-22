package com.example.huiqixue.coderish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Huiqi Xue on 2018/7/13.
 */

/**
 * By default, this adapter implements a table without external border, horizontally scrolled.
 * Anyone wishes to change that should use {@link #setHasExternalBorder(boolean)}
 * and {@link #setHorizontalScroll(boolean)}.
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    private DataCalculator dataCalculator;

    private int groupSize;

    private int itemCount;

    private boolean hasExternalBorder = false;

    private boolean horizontalScroll = true;

    private int[] cellMargins; // { left, top, right, bottom }

    private LinearLayout.LayoutParams textViewParams; // Just to cache an instance for speed

    private LinearLayout.LayoutParams cellParams; // Just to cache an instance for speed

    private int borderColor = 0xffdddddd; // Gray

    private int cellColor = 0xffffffff; // White

    private boolean isColumnWidthWrapContent = true;

    private int[] colomnWidths;

    public interface DataCalculator {

        String calculateData(int row, int column);

        Typeface typeFaceAt(int row, int column);
    }

    TableAdapter(int rowCount, int columnCount, DataCalculator dataCalculator) {
        this.dataCalculator = dataCalculator;
        this.groupSize = rowCount;
        this.itemCount = columnCount;
        this.textViewParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        this.cellParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        setCellSpace(10, 10, 10, 10);
        setCellBorder(1, 1, 1, 1);
    }

    public void setCellSpace(int left, int top, int right, int bottom) {
        textViewParams.setMargins(left, top, right, bottom);
    }

    public void setCellBorder(int left, int top, int right, int bottom) {
        cellMargins = new int[]{left, top, right, bottom};
        cellParams.setMargins(left, top, right, bottom);
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setCellColor(int cellColor) {
        this.cellColor = cellColor;
    }

    public void setHasExternalBorder(boolean hasExternalBorder) {
        this.hasExternalBorder = hasExternalBorder;
    }

    /**
     * If the user change {@link #horizontalScroll} to false, then switch {@link #itemCount} and
     * {@link #groupSize}.
     *
     * @param horizontalScroll if true, the recyclerView will be scrolled horizontally
     */
    public void setHorizontalScroll(boolean horizontalScroll) {
        if (!horizontalScroll) {
            this.horizontalScroll = horizontalScroll;
            int temp = this.itemCount;
            this.itemCount = this.groupSize;
            this.groupSize = temp;
        }
    }

    public void setHasHorizontalLines(boolean hasHorizontalLines) {
        if (!hasHorizontalLines) {
            cellMargins[1] = cellMargins[3] = 0;
            cellParams.setMargins(
                    cellMargins[0],
                    cellMargins[1],
                    cellMargins[2],
                    cellMargins[3]);
        }
    }

    public void setHasVerticalLines(boolean hasVerticalLines) {
        if (!hasVerticalLines) {
            cellMargins[0] = cellMargins[2] = 0;
            cellParams.setMargins(
                    cellMargins[0],
                    cellMargins[1],
                    cellMargins[2],
                    cellMargins[3]);
        }
    }

    public void setColomnWidths(int[] colomnWidths) {
        this.isColumnWidthWrapContent = false;
        this.colomnWidths = colomnWidths;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cellGroup;
        List<LinearLayout> cellList;
        List<TextView> textViewList;

        public ViewHolder(LinearLayout cellGroup, List<LinearLayout> cellList, List<TextView> textViewList) {
            super(cellGroup);
            this.cellGroup = cellGroup;
            this.cellList = cellList;
            this.textViewList = textViewList;
        }
    }

    /**
     * Because this adapter implements a table, so every itemView in the recyclerView will be
     * complicated. Basically following the formula:
     * cellGroup.cell.textView
     * (Every itemView is a (TableLayout) table, and it's children are listed above.)
     * table: a table with one column
     * tableRow: a row with one textView as its child
     * outerLinearLayout: has a background color of black, to implement borders
     * innerLinearLayout: has a background color of white, to control edge space
     * textView: to hold data for the table
     *
     * @param parent
     * @param viewType
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public TableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LinearLayout cellGroup = new LinearLayout(context);
        cellGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cellGroup.setBackgroundColor(borderColor); // Border color
        if (horizontalScroll)
            cellGroup.setOrientation(LinearLayout.VERTICAL);
        else
            cellGroup.setOrientation(LinearLayout.HORIZONTAL);

        List<TextView> textViewList = new ArrayList<>(groupSize);
        List<LinearLayout> cellList = new ArrayList<>(groupSize);

        for (int i = 0; i < groupSize; i++) {

            LinearLayout cell = new LinearLayout(context);
            cell.setBackgroundColor(cellColor);
            cell.setLayoutParams(cellParams);

            TextView textView = new TextView(context);
            textView.setBackgroundColor(0x00000000); // Transparent
            textView.setLayoutParams(textViewParams);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            cell.addView(textView);
            cellGroup.addView(cell);

            textViewList.add(textView);
            cellList.add(cell);
        }
        return new ViewHolder(cellGroup, cellList, textViewList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // If it has no external border, set margins to show table borders correctly
        if (!hasExternalBorder) {
            List<LinearLayout> cellList = holder.cellList;

            if (horizontalScroll) {
                //Now an itemGroup is a column, all items in it have the same left, right margin

                int left = this.cellMargins[0];
                int right = this.cellMargins[2];
                if (position == 0)
                    left = 0;
                else if (position == itemCount - 1)
                    right = 0;

                cellParams.setMargins(left, 0, right, this.cellMargins[3]);
                cellList.get(0).setLayoutParams(cellParams);

                int end = groupSize - 1;
                for (int i = 1; i < end; i++) {
                    cellParams.setMargins(left, this.cellMargins[1], right, this.cellMargins[3]);
                    cellList.get(i).setLayoutParams(cellParams);
                }

                cellParams.setMargins(left, this.cellMargins[1], right, 0);
                cellList.get(end).setLayoutParams(cellParams);
            } else {
                //Now an itemGroup is a row, all items in it have the same top, bottom margin

                int top = this.cellMargins[1];
                int bottom = this.cellMargins[3];
                if (position == 0)
                    top = 0;
                else if (position == itemCount - 1)
                    bottom = 0;

                cellParams.setMargins(0, top, this.cellMargins[2], bottom);
                cellList.get(0).setLayoutParams(cellParams);

                int end = groupSize - 1;
                for (int i = 1; i < end; i++) {
                    cellParams.setMargins(this.cellMargins[0], top, this.cellMargins[2], bottom);
                    cellList.get(i).setLayoutParams(cellParams);
                }

                cellParams.setMargins(this.cellMargins[0], top, 0, bottom);
                cellList.get(end).setLayoutParams(cellParams);
            }
        }

        // If user defines the widths, set widths correctly
        if (!isColumnWidthWrapContent) {
            List<TextView> textViewList = holder.textViewList;
            if (horizontalScroll) {
                int width = colomnWidths[position];
                for (int i = 0; i < groupSize; i++) {
                    textViewList.get(i).setWidth(width);
                }
            } else {
                for (int i = 0; i < groupSize; i++) {
                    textViewList.get(i).setWidth(colomnWidths[i]);
                }
            }
        }

        // Set table data at (row i, column j)
        if (horizontalScroll) {
            int j = position + 1;
            for (int i = 1; i <= groupSize; i++) {
                TextView textView = holder.textViewList.get(i - 1);
                textView.setText(dataCalculator.calculateData(i, j));
                textView.setTypeface(dataCalculator.typeFaceAt(i, j));
            }
        } else {
            int i = position + 1;
            for (int j = 1; j <= groupSize; j++) {
                TextView textView = holder.textViewList.get(j - 1);
                textView.setText(dataCalculator.calculateData(i, j));
                textView.setTypeface(dataCalculator.typeFaceAt(i, j));
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
