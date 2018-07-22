package com.example.huiqixue.coderish;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.cert.CertificateNotYetValidException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huiqi Xue on 2018/4/20.
 */

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {
    private List<String> stringList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        @TargetApi(Build.VERSION_CODES.M)
        public ViewHolder(TextView itemView) {
            super(itemView);
            this.textView = itemView;
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
        }
    }

    public List<String> getStringList() {
        return stringList;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
}
