/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package aspros.app.aslib.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import aspros.app.aslib.R;


public class XHeader extends IXHeader
{
    private View container;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;


    private final int ROTATE_ANIM_DURATION = 180;


    public XHeader(Context context)
    {
        super(context);
        initView(context);
    }

    private void initView(Context context)
    {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        container = LayoutInflater.from(context).inflate(R.layout.view_xlistview_header, null);
        addView(container, lp);


        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }


    @Override
    public void setStateNormal()
    {
        mArrowImageView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mRotateDownAnim);
        mHintTextView.setText(R.string.xlistview_header_hint_normal);

    }

    @Override
    public void setStateReady()
    {
        mArrowImageView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mArrowImageView.clearAnimation();
        mArrowImageView.startAnimation(mRotateUpAnim);
        mHintTextView.setText(R.string.xlistview_header_hint_ready);
    }

    @Override
    public void setStateRefreshing()
    {
        mArrowImageView.clearAnimation();
        mArrowImageView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mHintTextView.setText(R.string.xlistview_header_hint_loading);
    }

    @Override
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

    @Override
    public int getVisiableHeight()
    {
        return container.getHeight();
    }


}
