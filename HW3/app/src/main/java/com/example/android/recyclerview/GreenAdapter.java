/*
 *Reference:Udacity ud851-T03.07-Solution-RecyclerViewClickHandling
 *Created by wahaha on 09/04/2017.
 *Copyright 2017 wahaha. All rights reserved.Created by wahaha on 09/04/2017.
 */
package com.example.android.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public GreenAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 1;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        /* Create View*/

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);

        switch (MainActivity.flag[position])
        {
            //bindview with color 0=white(default),1=Gray,2=Red
            case 0:holder.itemView.setBackgroundColor(Color.WHITE);
                break;
            case 1:holder.itemView.setBackgroundColor(Color.GRAY);
                break;
            case 2:holder.itemView.setBackgroundColor(Color.RED);
                break;
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder
        implements OnClickListener {

        TextView listItemNumberView;


        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            itemView.setOnClickListener(this);
        }
        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex+1));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
