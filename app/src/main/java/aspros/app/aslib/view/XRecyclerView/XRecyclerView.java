package aspros.app.aslib.view.XRecyclerView;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.Scroller;


/**
 * Created by aspros on 2017/4/17.
 */

public class XRecyclerView extends RecyclerView
{
    private Scroller scroller;
    private float lastY = -1;
    private int headerViewHeight;
    private IXHeader xHeader;
    private XAdapter xAdapter;

    private final static int SCROLL_DURATION = 400;
    private final static float OFFSET_RADIO = 1.8f;

    protected boolean enablePullRefresh = true;
    protected boolean pullRefreshing = false;

    public PullRefreshListener refreshListener;

    private float mPhysicalCoeff;
    private float mFlingFriction = ViewConfiguration.getScrollFriction();

    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)

    private double getSplineDeceleration(int velocity)
    {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    private double getSplineFlingDistance(int velocity)
    {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * mPhysicalCoeff * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    /* Returns the duration, expressed in milliseconds */
    private int getSplineFlingDuration(int velocity)
    {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return (int) (1000.0 * Math.exp(l / decelMinusOne));
    }



    public void setPullRefresh(PullRefreshListener pullRefreshing)
    {
        this.refreshListener = pullRefreshing;
        xAdapter.setHeader(xHeader);
        enablePullRefresh=true;


    }

    public void disablePullRefresh()
    {
        enablePullRefresh=false;
    }


    public XRecyclerView(Context context)
    {
        super(context);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        final float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
        mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi * 0.84f; // look and feel tuning


        setOverScrollMode(OVER_SCROLL_NEVER);
        scroller = new Scroller(context);

        xHeader = new XHeader(context);
        xHeader.getViewTreeObserver()
               .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
               {
                   @Override
                   public void onGlobalLayout()
                   {
                       headerViewHeight = xHeader.getVisiableHeight();
                       System.out.println("GetmHeadViewHeight = " + headerViewHeight);
                       getViewTreeObserver().removeGlobalOnLayoutListener(this);
                       xHeader.setVisiableHeight(0);
                   }
               });

        xAdapter = new XAdapter();

        addOnScrollListener(new OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {

                super.onScrollStateChanged(recyclerView, newState);

                if (getAdapter() != null && newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    public void setAdapter(Adapter adapter)
    {
        xAdapter.setAdapter(adapter);
        super.setAdapter(xAdapter);
    }

    protected void updateHeaderHeight(float delta)
    {
        xHeader.setVisiableHeight((int) delta + xHeader.getVisiableHeight());
        if (enablePullRefresh && !pullRefreshing)
        {
            if (xHeader.getVisiableHeight() > headerViewHeight)
            {
                xHeader.setState(XHeader.STATE_READY);
            }
            else
            {
                xHeader.setState(XHeader.STATE_NORMAL);
            }
        }
        scrollToPosition(0);
    }

    protected void resetHeaderHeight()
    {
        int height = xHeader.getVisiableHeight();
        //		System.out.println("height = " + height);
        //		System.out.println("mHeadViewHeight = " + mHeaderViewHeight);
        if (height == 0) // not visible.
        {
            return;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (pullRefreshing && height <= headerViewHeight)
        {
            return;
        }
        int finalHeight = 0;
        if (pullRefreshing && height > headerViewHeight)
        {
            finalHeight = headerViewHeight;
        }
//        Log.d("xlistview", height + " -- resetHeaderHeight-->" + (finalHeight - height));
        scroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);

        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {

        if (lastY == -1)
        {
            lastY = ev.getRawY();
        }

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                final float deltaY = ev.getRawY() - lastY;
                lastY = ev.getRawY();
                if (enablePullRefresh && (getFirstVisiblePosition() == 0 || getFirstVisiblePosition() == 1 && xHeader
                        .getVisiableHeight() == 0) && (xHeader.getVisiableHeight() > 0 || deltaY > 0) && !pullRefreshing)
                {
                    updateHeaderHeight(deltaY / OFFSET_RADIO);

                }
                break;
            default:
                lastY = -1;
                if (getFirstVisiblePosition() == 0)
                {
                    startOnRefresh();
                    resetHeaderHeight();
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    public int getFirstVisiblePosition()
    {
        if (getLayoutManager() instanceof LinearLayoutManager)
        {
            return ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        }
        if (getLayoutManager() instanceof GridLayoutManager)
        {
            return ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        }
        return -1;
    }

    protected void startOnRefresh()
    {
        if (enablePullRefresh && xHeader.getVisiableHeight() > headerViewHeight && !pullRefreshing)
        {
            pullRefreshing = true;
            xHeader.setState(XHeader.STATE_REFRESHING);
            if (refreshListener != null)
            {
                refreshListener.onRefresh();
            }
        }
    }

    public void startRefresh()
    {
        pullRefreshing = true;
        xHeader.setState(XHeader.STATE_REFRESHING);
        if (refreshListener != null)
        {
            refreshListener.onRefresh();
        }
    }

    public void stopRefresh()
    {
        if (pullRefreshing)
        {
            pullRefreshing = false;
            resetHeaderHeight();
        }
        xHeader.setState(XHeader.STATE_NORMAL);
    }

    @Override
    public void computeScroll()
    {
        if (scroller.computeScrollOffset())
        {
            xHeader.setVisiableHeight(scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }



}
