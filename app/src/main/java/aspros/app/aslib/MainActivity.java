package aspros.app.aslib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import aspros.app.aslibrary.ui.BaseActivity;
import aspros.app.aslibrary.ui.view.SwipeBackLayout;
import aspros.app.aslibrary.util.LogUtil;
import butterknife.Bind;

public class MainActivity extends BaseActivity
{


    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId()
    {
        setSwipeBack(false);
        return R.layout.activity_main;
    }

    @Override
    public void initView()
    {
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
                        Intent intent=new Intent(context,ViewpagerActivity.class);
                        context.startActivity(intent);
                    }
                });
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

        TextView textView;
        public MyHolder(View itemView)
        {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textview);
        }
    }
}
