package aspros.app.aslib.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import aspros.app.aslib.R;

/**
 * Created by aspros on 2017/8/11.
 */

public class AlipayActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener
{
    RecyclerView recyclerView;
    Context context;
    AppBarLayout appBarLayout;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        context=this;
        appBarLayout= (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
        appBarLayout.setOnDragListener(new View.OnDragListener()
        {
            @Override
            public boolean onDrag(View v, DragEvent event)
            {
                return false;
            }
        });
        recyclerView  = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new XAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("onScrollChange",dy+","+ViewCompat.canScrollVertically(recyclerView, -1));
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
    {
//        Log.d("onOffsetChanged",verticalOffset+","+recyclerView.getScrollY()+","+ViewCompat.canScrollVertically(recyclerView, -1));

    }


    class XAdapter extends RecyclerView.Adapter<MHolder>
    {

        @Override
        public MHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            return new MHolder(LayoutInflater.from(context).inflate(R.layout.view_text,null));
        }

        @Override
        public void onBindViewHolder(MHolder holder, int position)
        {
            holder.textView.setText("test "+ position);
        }

        @Override
        public int getItemCount()
        {
            return 20;
        }
    }
    class MHolder extends RecyclerView.ViewHolder
    {

        TextView textView;
        public MHolder(View itemView)
        {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
