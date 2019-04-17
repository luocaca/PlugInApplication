package me.luocaca.pluginapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class DispatchScrollView extends ScrollView {
    private static final String TAG = "DispatchScrollView";

    public DispatchScrollView(Context context) {
        super(context);
    }

    public DispatchScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Log.i(TAG, "onScrollChanged: t=" + t);

    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        Log.i(TAG, "onOverScrolled: scrollY="+scrollY);
        Log.i(TAG, "onOverScrolled: clampedY="+clampedY);


    }
}
