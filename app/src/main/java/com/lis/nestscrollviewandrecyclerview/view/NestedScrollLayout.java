package com.lis.nestscrollviewandrecyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

public class NestedScrollLayout extends NestedScrollView {
    public NestedScrollLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    private View topView;
    private ViewGroup contentView;

    //布局加载完后
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = ((ViewGroup) getChildAt(0)).getChildAt(0);
        contentView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 调整contentView的高度为父容器高度，使之填充布局，避免父容器滚动后出现空白
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.height = getMeasuredHeight();
        //设置contentView的高度为全屏幕的高度，实现TabLayout顶部悬浮
        contentView.setLayoutParams(layoutParams);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //scrollview顶部滑动
        //这里实现scrollview顶部没有隐藏时，滑动底部的RecyclerView时，scrollview滑动
        boolean onScroll = dy > 0 && getScrollY() < topView.getMeasuredHeight();
        if (onScroll) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }
}
