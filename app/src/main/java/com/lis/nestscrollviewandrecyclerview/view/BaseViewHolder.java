package com.lis.nestscrollviewandrecyclerview.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private TextView view;

    public BaseViewHolder(View view) {
        super((View) view);
        this.view = (TextView) view;
    }

    public void bind(@NonNull String item) {
        view.setText(item);
    }
}