package aspros.app.aslib;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import aspros.app.aslibrary.ui.BaseFragment;
import aspros.app.aslibrary.ui.view.SwipeBackLayout;
import aspros.app.aslibrary.util.LogUtil;
import butterknife.Bind;

/**
 * Created by aspros on 2017/2/20.
 */

public class ListFragment extends BaseFragment
{

    @Bind(R.id.framelayout)
    FrameLayout framelayout;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;


    @Bind(R.id.swiplayout)
    SwipeBackLayout swipeBackLayout;


    XListViewHeader header;

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_list;
    }

    @Override
    public void initView()
    {
        header=new XListViewHeader(context);
        framelayout.addView(header,0);

        swipeBackLayout.setDragEdge(SwipeBackLayout.TOP);
        swipeBackLayout.setRefreshListener(header);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new RecyclerView.Adapter()
        {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {
                TextView textView=new TextView(context);
                textView.setPadding(50,50,20,50);

                return new MyHolder(textView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
            {

                ((TextView)holder.itemView  ).setText(position+"   text");
            }

            @Override
            public int getItemCount()
            {
                return 30;
            }
        });
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        public MyHolder(View itemView)
        {
            super(itemView);
        }
    }
}
