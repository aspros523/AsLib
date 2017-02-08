package aspros.app.aslibrary.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import aspros.app.aslibrary.R;
import butterknife.ButterKnife;

/**
 * Created by aspors on 12/16/15.
 */
public abstract class BaseFragment extends Fragment implements LoadStatusCallback
{

    protected Context context;
    protected Activity activity;

    protected ProgressDialog progressDialog;
    protected RelativeLayout parentContainer;
    private View statusLayout;
    private TextView statusTv;
    private int status;
    protected OnRefreshListener onRefreshListener;


    public interface OnRefreshListener
    {
        void onRefresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        parentContainer = (RelativeLayout) view.findViewById(R.id.container);
        statusLayout = view.findViewById(R.id.status_layout);
        statusTv = (TextView) view.findViewById(R.id.status_tv);
        view.findViewById(R.id.click_view).setOnClickListener(v -> {
            if (status == STATUS_LOADFAIL && onRefreshListener != null)
            {
                onRefreshListener.onRefresh();
            }
        });
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("请稍后...");

        LayoutInflater.from(context).inflate(getLayoutId(), parentContainer, true);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    public abstract int getLayoutId();

    public abstract void initView();


    @Override
    public void onLoading()
    {
        status = STATUS_LOADING;
        statusTv.setText("正在加载中...");
        statusLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaded()
    {
        status = STATUS_LOADED;
        statusLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoadFailed()
    {
        status = STATUS_LOADFAIL;
        statusTv.setText("获取信息失败了/(ㄒoㄒ)/\n点击重新加载");
        statusLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadNull()
    {
        status = STATUS_LOADNULL;
        statusTv.setText("该分类下没有相关信息~");
        statusLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

}
