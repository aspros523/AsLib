package aspros.app.aslib.view.Group;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import aspros.app.aslib.DensityUtil;
import aspros.app.aslib.R;

/**
 * Created by aspros on 2017/4/21.
 */

public class Group extends ViewGroup
{
    private Paint paint;
    private String title;
    private int titleColor;
    private int titleHeight;
    private int titleSize;
    private int dividerColor;
    private int dividerSize;
    private int dividerMargin;
    private RectF titleRect;

    public Group(Context context)
    {
        this(context, null);
    }

    public Group(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public Group(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Group, defStyleAttr, 0);

        title = a.getString(R.styleable.Group_title);
        titleColor = a.getColor(R.styleable.Group_titleColor, context.getResources()
                                                                     .getColor(R.color.color_6));
        titleSize = a.getDimensionPixelSize(R.styleable.Group_titleSize, DensityUtil.sp2px(context,12));
        titleHeight = a.getDimensionPixelSize(R.styleable.Group_titleHeight, DensityUtil.dip2px(context, 35));

        dividerColor = a.getColor(R.styleable.Group_dividerColor, context.getResources()
                                                                         .getColor(R.color.divider));
        dividerSize = a.getDimensionPixelSize(R.styleable.Group_dividerSize, DensityUtil.dip2px(context, 0.8f));
        dividerMargin = a.getDimensionPixelSize(R.styleable.Group_dividerMargin, DensityUtil.dip2px(context, 12));

        a.recycle();

        paint=new Paint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {



        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();


        int wantHeight = titleHeight+paddingTop+paddingBottom;
        int wantWidth = resolveSize(0, widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++)
        {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE)
            {
                continue;
            }
            LayoutParams lp = childView.getLayoutParams();
            childView.measure(getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, lp.width), getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, lp.height));

            int childHeight = childView.getMeasuredHeight();
            wantHeight += childHeight + dividerSize;
        }
        wantHeight += dividerSize;


        setMeasuredDimension(wantWidth, wantHeight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        int childLeft = paddingLeft;
        int childTop = titleHeight+paddingTop+dividerSize;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i)
        {

            final View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE)
            {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childTop+=childHeight+dividerSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(dividerSize);
        paint.setColor(titleColor);
        paint.setTextSize(titleSize);
        paint.setTextAlign(Paint.Align.LEFT);

        int paddingTop = getPaddingTop();

        if(titleRect==null)
        {
            titleRect=new RectF(dividerMargin,paddingTop+titleHeight/4,getWidth(),titleHeight);

        }

        if(!TextUtils.isEmpty(title))
        {
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
             int baseline = (int) ((titleRect.bottom + titleRect.top - fontMetrics.bottom - fontMetrics.top) / 2);

            canvas.drawText(title, titleRect.left, baseline, paint);
        }
        paint.setColor(dividerColor);


        int childTop = titleHeight+paddingTop+dividerSize;
        canvas.drawLine(0,childTop-dividerSize,getWidth(),childTop-dividerSize,paint);

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i)
        {
            final View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE)
            {
                continue;
            }
            int childHeight = childView.getMeasuredHeight();


            if(i>0)
            {
                canvas.drawLine(dividerMargin,childTop-dividerSize,getWidth(),childTop-dividerSize,paint);
            }
            childTop+=childHeight+dividerSize;
        }
        canvas.drawLine(0,childTop-dividerSize,getWidth(),childTop-dividerSize,paint);
    }
}
