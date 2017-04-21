package aspros.app.aslib.view.XRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by aspros on 2017/4/17.
 */

public class XAdapter extends RecyclerView.Adapter
{
    public final int HEADER_TYPE=-1000;
    public final int FOOTER_TYPE=-1001;

    private int hasHeader,hasFooter;
    private RecyclerView.Adapter adapter;

    private XViewHolder header,footer;

    public void setHeader(View view)
    {
        if(view==null)
        {
            hasHeader=0;
            return;
        }
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header=new XViewHolder(view);
        hasHeader=1;
    }

    public void setFooter(View view)
    {
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        footer=new XViewHolder(view);
        hasFooter=1;
    }

    public void setAdapter(RecyclerView.Adapter adapter)
    {
        this.adapter=adapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType==HEADER_TYPE)
        {

            return header;
        }
        if(viewType==FOOTER_TYPE)
        {
            return footer;
        }
        return adapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(getItemViewType(position)==HEADER_TYPE)
        {
            return;
        }
        if(getItemViewType(position)==FOOTER_TYPE)
        {
            return;
        }

        adapter.onBindViewHolder(holder,position-hasHeader);

    }

    @Override
    public int getItemViewType(int position)
    {
        if(hasHeader==1 && position==0)
        {
            return HEADER_TYPE;
        }
        if(hasFooter==1 && position==adapter.getItemCount()+hasHeader)
        {
            return FOOTER_TYPE;
        }
        return adapter.getItemViewType(position-hasHeader);
    }

    @Override
    public int getItemCount()
    {
        return adapter.getItemCount()+hasFooter+hasHeader;
    }


    public class XViewHolder extends RecyclerView.ViewHolder
    {

        public XViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
