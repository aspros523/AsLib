package aspros.app.aslib.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by aspros on 2017/9/5.
 */

public class FlipableView extends ViewGroup
{
    private Scroller scroller;
    private int width;
    private int height;
    private int curIndex = 0;
    private boolean fliping;
    private Camera camera;
    private Matrix matrix;

    public FlipableView(Context context)
    {
        this(context,null);
    }

    public FlipableView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public FlipableView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context)
    {
        scroller=new Scroller(context,new LinearInterpolator());
        camera=new Camera();
        matrix=new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if(getChildCount() !=2)
        {
            throw new RuntimeException("必须包含两个直接子View");
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int top = 0;
        for(int i=0;i<getChildCount();i++)
        {
            View child = getChildAt(i);
            child.layout(0,top,child.getMeasuredWidth(),top+child.getMeasuredHeight());
            top+=child.getMeasuredHeight();
        }
    }

    public void flip()
    {
        if(fliping)
        {
            return;
        }
        fliping = true;
        if(curIndex==0)
        {
            scroller.startScroll(0,getScrollY(),0,height,600);
        }
        else
        {
            scroller.startScroll(0,getScrollY(),0,-height,600);
        }
        curIndex ^=1;
        postInvalidate();
    }

    @Override
    public void computeScroll()
    {
        if (scroller.computeScrollOffset())
        {
            scrollTo(0,scroller.getCurrY());
            postInvalidate();
        }
        else
        {
            fliping = false;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        for(int i=0;i<getChildCount();i++)
        {
            drawItem(canvas,i,getDrawingTime());
        }
    }

    private void drawItem(Canvas canvas,int i,long time)
    {
        int curScreenY = height * i;

        float centerX = width /2;
        float centerY = (getScrollY() > curScreenY) ? curScreenY + height : curScreenY;
        float degree = 90.0f * (getScrollY() - curScreenY) / height;

        canvas.save();

        camera.save();
        camera.rotateX(degree);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX,-centerY);
        matrix.postTranslate(centerX,centerY);
        canvas.concat(matrix);
        drawChild(canvas,getChildAt(i),time);
        canvas.restore();

    }
}
