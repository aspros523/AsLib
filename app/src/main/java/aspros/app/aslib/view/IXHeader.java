package aspros.app.aslib.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import aspros.app.aslib.R;


/**
 * Created by aspros on 2017/4/20.
 */

public abstract class IXHeader extends FrameLayout
{

    private View container;
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    protected int mState = STATE_NORMAL;

    public IXHeader(@NonNull Context context)
    {
        super(context);
        init(context);
    }

    private void init(Context context)
    {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        container = initView(context);
        addView(container, lp);
    }



    public void setState(int state)
    {
        if (state == mState)
        {
            return;
        }
        switch (state)
        {
            case STATE_NORMAL:
                setStateNormal();
                break;
            case STATE_READY:
                setStateReady();
                break;
            case STATE_REFRESHING:
                setStateRefreshing();
                break;
            default:
        }

        mState = state;
    }


    public void setVisiableHeight(int height)
    {
        if (height < 0)
        {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) container.getLayoutParams();
        lp.height = height;
        container.setLayoutParams(lp);
    }

    public int getVisiableHeight()
    {
        return container.getHeight();
    }


    public abstract View initView(Context context);
    public abstract void setStateNormal();
    public abstract void setStateReady();
    public abstract void setStateRefreshing();
}
