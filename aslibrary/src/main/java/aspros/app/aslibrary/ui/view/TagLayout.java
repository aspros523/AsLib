package aspros.app.aslibrary.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import aspros.app.aslibrary.R;


/**
 * Created by aspros on 15/10/5.
 * 标签容器
 */
public class TagLayout extends ViewGroup
{

    private int DEFAULT_LINESPACING = 20;
    private int DEFAULT_TAGSPACING = 20;
    private int lineSpacing;
    private int tagSpacing;

    public TagLayout(Context context)
    {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagLayout, defStyleAttr, 0);

        lineSpacing = a.getDimensionPixelSize(R.styleable.TagLayout_lineSpacing, DEFAULT_LINESPACING);
        tagSpacing = a.getDimensionPixelSize(R.styleable.TagLayout_tagSpacing, DEFAULT_TAGSPACING);

        a.recycle();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int wantHeight = 0;
        int wantWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        for (int i = 0; i < getChildCount(); i++)
        {
            final View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE)
            {
                continue;
            }

            LayoutParams params = childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, params.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, params.height)
            );

            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();
            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > wantWidth)
            {
                childLeft = paddingLeft+childWidth + tagSpacing;
                childTop += lineSpacing + childHeight;
                lineHeight = childHeight;
            }
            else
            {
                childLeft += childWidth + tagSpacing;
            }


        }
        wantHeight = childTop + lineHeight + paddingBottom;
        setMeasuredDimension(wantWidth, resolveSize(wantHeight, heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int width = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i)
        {

            final View childView = getChildAt(i);

            if (childView.getVisibility() == View.GONE)
            {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);

            if (childLeft + childWidth + paddingRight > width)
            {
                childLeft = paddingLeft;
                childTop += lineSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + tagSpacing;
        }
    }
}
