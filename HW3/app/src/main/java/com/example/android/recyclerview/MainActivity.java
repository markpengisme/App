/*
 *Reference:Udacity ud851-T03.07-Solution-RecyclerViewClickHandling
 *Created by wahaha on 09/04/2017.
 *Copyright 2017 wahaha. All rights reserved.Created by wahaha on 09/04/2017.
 */
package com.example.android.recyclerview;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity
        implements GreenAdapter.ListItemClickListener{

    static int NUM_LIST_ITEMS = 100,lower=0,upper=99,ans,click,flag[],count=0;
    //Number of Item ,lower bound,upper bound,answer by random,View's color ,guess times

    private GreenAdapter mAdapter;
    private RecyclerView mNumbersList;
    private Toast mToast;
    private TextView mTV;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        /*  initialization every view and variable  */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new GreenAdapter(NUM_LIST_ITEMS,this );
        mNumbersList.setAdapter(mAdapter);
        showToast("Game Start");
        flag = new int[NUM_LIST_ITEMS];
        ans = (int) (Math.random() * NUM_LIST_ITEMS);
        mTV=(TextView)findViewById(R.id.show_ans);
        mTV.setText("ANS:"+Integer.toString(ans+1));

        /*  button click and reset game */
        Button bt=(Button)findViewById(R.id.bt_1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et=(EditText)findViewById(R.id.et_1);
                NUM_LIST_ITEMS=Integer.valueOf(et.getText().toString()).intValue();
                mAdapter = new GreenAdapter(NUM_LIST_ITEMS,MainActivity.this);
                mNumbersList.setAdapter(mAdapter);
                showToast("New Game Start");
                flag = new int[NUM_LIST_ITEMS];
                upper=NUM_LIST_ITEMS;
                lower=0;
                count=0;
                ans = (int) (Math.random() * NUM_LIST_ITEMS);
                mTV.setText("ANS:"+Integer.toString(ans+1));
            }
        });
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Item click event

        if(flag[clickedItemIndex]==0 &&flag[ans]!=2)
        {
            /*  only white color item can do thing    */
            count++;
            String toastMessage = "Item #" + (clickedItemIndex+1) + " clicked.";
            showToast(toastMessage);
            click = clickedItemIndex;
            if (click == ans) {
                flag[clickedItemIndex] = 2;
                showToast("You win,guess "+count+" times");
            }
            if (click > ans) {
                for (int i = upper; i >= click; i--) {
                    flag[i] = 1;
                }
                upper = click;
            }
            if (click < ans) {
                for (int i = lower; i <= click; i++) {
                    flag[i] = 1;
                }
                lower = click;
            }
            mAdapter.notifyDataSetChanged();
        }
        else if(flag[ans]==2){
            showToast("Please Restart game");
        }
        else{
            showToast("Don't touch Gray");
        }
    }


    public void showToast(String message) {

        if (mToast != null) {
            mToast.cancel();
        }
        mToast=Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
