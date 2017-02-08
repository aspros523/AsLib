package aspros.app.aslibrary.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import aspros.app.aslibrary.R;
import aspros.app.aslibrary.base.BaseModel;
import aspros.app.aslibrary.base.BasePresenter;
import aspros.app.aslibrary.base.BaseView;
import aspros.app.aslibrary.ui.view.SwipeBackLayout;
import aspros.app.aslibrary.util.TUtil;
import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity
{
    public T mPresenter;
    public E mModel;
    public Context mContext;

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    protected boolean swipeBack = true;


    protected Activity activity;
    protected Context context;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());

        if (savedInstanceState != null)
        {

        }
        activity = this;
        context = this;

        ButterKnife.bind(this);
        mContext = this;

        this.initView();
        if (this instanceof BaseView)
        {
            mPresenter = TUtil.getT(this, 0);
            mModel = TUtil.getT(this, 1);
            mPresenter.setVM(this, mModel);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mPresenter != null)
        {
            mPresenter.onDestroy();
        }
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();


    }

    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void setContentView(int layoutResID)
    {
        if (!swipeBack)
        {
            super.setContentView(layoutResID);
            return;
        }
        super.setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        view.setBackgroundColor(getResources().getColor(R.color.main_bg));
        swipeBackLayout.addView(view);
    }

    private View getContainer()
    {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    public void setSwipeBack(boolean swipeBack)
    {
        this.swipeBack = swipeBack;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    public abstract int getLayoutId();

    public abstract void initView();


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {

    }
}
