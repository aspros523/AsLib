package aspros.app.aslib;

import android.support.v4.app.*;
import android.support.v4.view.ViewPager;

import aspros.app.aslibrary.ui.BaseActivity;
import aspros.app.aslibrary.ui.view.SwipeBackLayout;
import butterknife.Bind;

/**
 * Created by aspros on 2017/2/20.
 */

public class ViewpagerActivity extends BaseActivity
{
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_viewpage;
    }

    @Override
    public void initView()
    {

        setDragEdge(SwipeBackLayout.LEFT);
        final MyAdapter adapter=new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }


    public class MyAdapter extends FragmentPagerAdapter
    {
        public ListFragment[] fragments=new ListFragment[4];
        public MyAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if(fragments[position]==null)
            {
                fragments[position]=new ListFragment();
            }
            return fragments[position];
        }

        @Override
        public int getCount()
        {
            return 4;
        }
    }



}
