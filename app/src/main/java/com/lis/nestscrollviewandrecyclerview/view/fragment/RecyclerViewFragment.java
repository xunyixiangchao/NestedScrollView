package com.lis.nestscrollviewandrecyclerview.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lis.nestscrollviewandrecyclerview.R;
import com.lis.nestscrollviewandrecyclerview.view.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final RecyclerAdapter adapter = new RecyclerAdapter(getData());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int THRESHOLD_LOAD_MORE = 3;
            private boolean hasLoadMore;

            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hasLoadMore = false;
                }

                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && !hasLoadMore) {
                    int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int offset = recyclerView.getAdapter().getItemCount() - lastPosition - 1;
                    if (offset <= THRESHOLD_LOAD_MORE) {
                        hasLoadMore = true;
                        adapter.data.addAll(getData());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return view;
    }

    private int i = 0;

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int tempI = i; i < tempI + 10; i++) {
            data.add("ChildView item " + i);
        }
        return data;
    }
}
