package pan.smart.smart.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


import pan.smart.smart.R;


/**
 * Created by anupamchugh on 09/02/16.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    private ArrayList<searchModelRecycler> dataSet;
   public Context mContext;
    private int total_types;

    View.OnClickListener itemListener;

    public SearchAdapter(ArrayList<searchModelRecycler> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {



        CardView cardView;
        TextView CitytName;
        TextView Capital;
        int id;




        public TextTypeViewHolder(View itemView) {
            super(itemView);


            this.cardView = (CardView) itemView.findViewById(R.id.card_view);


            this.CitytName   = (TextView) itemView.findViewById(R.id.CitytName);
            this.Capital     = (TextView) itemView.findViewById(R.id.Capital);


        }

    }







    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycler_row, parent, false);
                return new TextTypeViewHolder(view);




    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {


        if (itemListener != null) {
            holder.itemView.setOnClickListener(itemListener);
        }

        searchModelRecycler object = dataSet.get(listPosition);

        if (object != null)
        {
                    ((TextTypeViewHolder) holder).CitytName.setText((object).getCountryName());
                    ((TextTypeViewHolder) holder).Capital.setText((object).getCapital());

        }
    }

    public void update(ArrayList<searchModelRecycler> list )
    {
        this.dataSet=list;
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        this.itemListener = clickListener;
    }



}
