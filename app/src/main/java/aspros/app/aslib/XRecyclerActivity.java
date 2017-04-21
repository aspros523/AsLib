package aspros.app.aslib;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import aspros.app.aslib.view.XRecyclerView.PullRefreshListener;
import aspros.app.aslib.view.XRecyclerView.XRecyclerView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class XRecyclerActivity extends AppCompatActivity
{


    XRecyclerView recyclerView;
    Context context;

    int size;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycler);

        recyclerView= (XRecyclerView) findViewById(R.id.recyclerview);
        context=this;
        initView();
    }

    public void initView()
    {
        size=50;
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new RecyclerView.Adapter()
        {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
            {

                return new  MyHolder(LayoutInflater.from(context).inflate(R.layout.view_text,parent,false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
            {

                ((MyHolder)holder).textView.setText(position+"   text");
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        recyclerView.disablePullRefresh();
                    }
                });
            }

            @Override
            public int getItemCount()
            {
                return size;
            }
        });

        recyclerView.setPullRefresh(new PullRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>()
                        {
                            @Override
                            public void call(Long aLong)
                            {
                                recyclerView.stopRefresh();
                            }
                        });
            }
        });
    }


    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView textView;
        public MyHolder(View itemView)
        {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
