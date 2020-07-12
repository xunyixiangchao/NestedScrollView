package com.lis.nestscrollviewandrecyclerview.utils;

import android.content.Context;
import android.view.ViewConfiguration;

/**
 * 惯性滑动处理速度与距离的工具类
 */
public class FlingHelper {
    private static float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
    private static float mFlingFriction = ViewConfiguration.getScrollFriction();
    private static float mPhysicalCoeff;

    public FlingHelper(Context context) {
        mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
    }

    private double getSplineDeceleration(int i) {
        return Math.log((double) ((0.35f * ((float) Math.abs(i))) / (mFlingFriction * mPhysicalCoeff)));
    }

    private double getSplineDecelerationByDistance(double d) {
        return ((((double) DECELERATION_RATE) - 1.0d) * Math.log(d / ((double) (mFlingFriction * mPhysicalCoeff)))) / ((double) DECELERATION_RATE);
    }

    /**
     * 根据速度求滑动距离
     *
     * @param i
     * @return
     */
    public double getSplineFlingDistance(int i) {
        return Math.exp(getSplineDeceleration(i) * (((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d))) * ((double) (mFlingFriction * mPhysicalCoeff));
    }

    /**
     * 根据滑动距离求速度
     *
     * @param d
     * @return
     */
    public int getVelocityByDistance(double d) {
        return Math.abs((int) (((Math.exp(getSplineDecelerationByDistance(d)) * ((double) mFlingFriction)) * ((double) mPhysicalCoeff)) / 0.3499999940395355d));
    }
}
