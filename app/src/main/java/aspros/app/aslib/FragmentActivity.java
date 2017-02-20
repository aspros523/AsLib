package aspros.app.aslib;

import android.widget.FrameLayout;

import aspros.app.aslibrary.ui.BaseActivity;
import butterknife.Bind;

/**
 * Created by aspros on 2017/2/20.
 */

public class FragmentActivity extends BaseActivity
{

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_fragment;
    }

    @Override
    public void initView()
    {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new ListFragment()).commit();
    }
}
