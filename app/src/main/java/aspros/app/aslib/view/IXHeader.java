package aspros.app.aslib.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by aspros on 2017/4/20.
 */

public abstract class IXHeader extends FrameLayout
{

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    protected int mState = STATE_NORMAL;

    public IXHeader(Context context)
    {
        super(context);
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
    public abstract void setStateNormal();
    public abstract void setStateReady();
    public abstract void setStateRefreshing();
    public abstract int getVisiableHeight();
    public abstract void setVisiableHeight(int height);
}
