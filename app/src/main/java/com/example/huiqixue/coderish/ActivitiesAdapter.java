package com.example.huiqixue.coderish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Huiqi Xue on 2018/4/20.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {

    // To add an activity, add related objects to the following two lists.
    // Add activity name here.
    static final String[] names = new String[]{
            "Unicode",
            "Alphabetical Order",
            "Hexadecimal Numbers"
    };

    // Add activity.class here
    static final Class[] activities = new Class[]{
            UnicodeActivity.class,
            AlphabeticalOrderActivity.class,
            HexadecimalActivity.class
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button startButton;


        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.item_text_view);
            textView.setTextSize(20);
            this.startButton = itemView.findViewById(R.id.item_button);
            startButton.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int finalPosition = position;
        holder.textView.setText(names[finalPosition]);
        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getCurrentContext(), activities[finalPosition]);
                MyApplication.getCurrentContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }


}
