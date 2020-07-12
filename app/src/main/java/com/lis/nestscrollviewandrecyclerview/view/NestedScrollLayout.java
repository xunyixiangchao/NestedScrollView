package com.lis.nestscrollviewandrecyclerview.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.lis.nestscrollviewandrecyclerview.utils.FlingHelper;

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

    FlingHelper mFlingHelper;
    int totalDy;

    private View topView;
    private ViewGroup contentView;

    boolean isStartFling = false;
    /**
     * 记录当前滑动的y轴加速度
     */
    private int velocityY = 0;

    private void init() {
        mFlingHelper = new FlingHelper(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (isStartFling) {
                        totalDy = 0;
                        isStartFling = false;
                    }
                    if (scrollY == 0) {
                        //top scroll 可以在此进行刷新
                    }
                    if (scrollY == (getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        //开始底部的滑动
                        dispatchChildFling();
                    }
                    //在RecyclerView fling 情况下，记录当前RecyclerView在Y轴的偏移
                    totalDy += scrollY - oldScrollY;
                }
            });
        }
    }

    private void dispatchChildFling() {
        if (velocityY != 0) {
            double splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY);
            //根据速度求的距离大于滑动的距离时
            if (splineFlingDistance > totalDy) {
                //求距离差的速度
                int velocity = mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy);
                //计算子View的惯性滑动
                childFling(velocity);
            }
        }
        totalDy = 0;
        velocityY = 0;

    }

    private void childFling(int velocity) {
        //获取底部的RecyclerView
        RecyclerView recyclerView = getChildRecyclerView(contentView);
        if (recyclerView != null) {
            recyclerView.fling(0, velocity);
        }
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            //这里一定要判断view.getClass()，因为viewpager2中有一个RecyclerViewImpl继承RecyclerView
            //不判断就会获取到这个对象，从而下方的RecyclerView不能惯性滑动
            if (view instanceof RecyclerView && view.getClass() == RecyclerView.class) {
                return (RecyclerView) view;
            } else if (view instanceof ViewGroup) {
                ViewGroup childRecyclerView = getChildRecyclerView((ViewGroup) view);
                if (childRecyclerView instanceof RecyclerView) {
                    return (RecyclerView) childRecyclerView;
                }
            }
            continue;
        }
        return null;
    }


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
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
                                  int type) {
        //scrollview顶部滑动
        //这里实现scrollview顶部没有隐藏时，滑动底部的RecyclerView时，scrollview滑动
        boolean onScroll = dy > 0 && getScrollY() < topView.getMeasuredHeight();
        if (onScroll) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            this.velocityY = 0;
        } else {
            this.velocityY = velocityY;
            isStartFling = true;
        }

    }

}
