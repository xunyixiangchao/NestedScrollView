package com.lis.nestscrollviewandrecyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FixedDisabledScrollRecyclerView extends RecyclerView {
    public FixedDisabledScrollRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }


    public FixedDisabledScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixedDisabledScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerAdapter adapter = new RecyclerAdapter(getBanner());
        setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    private List<String> getBanner() {
        List<String> data = new ArrayList<>();
        data.add("ParentView item 0");
        data.add("ParentView item 1");
        data.add("ParentView item 2");
        data.add("ParentView item 3");
        data.add("ParentView item 4");
        data.add("ParentView item 5");
        data.add("ParentView item 6");
        data.add("ParentView item 7");
        return data;
    }

}
