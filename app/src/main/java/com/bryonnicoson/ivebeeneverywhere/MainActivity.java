package com.bryonnicoson.ivebeeneverywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    // FIXME: 8/21/16 - Picasso.placeholder(drawable) starts centered in RecyclerView - XML view load order?

    public static class StateHolder extends RecyclerView.ViewHolder {

        public ImageView flag;
        public TextView name;

        public StateHolder(View itemView){
            super(itemView);
            flag = (ImageView)itemView.findViewById(R.id.state_flag);
            name = (TextView)itemView.findViewById(R.id.state_name);
        }
    }

    public static final String STATES = "states";
    private RecyclerView mStateRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutMangager;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<State, StateHolder> mFireBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view init
        mStateRecyclerView = (RecyclerView)findViewById(R.id.state_recyclerview);
        //GV mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutMangager = new GridLayoutManager(this, 4, GridLayoutManager.HORIZONTAL, false);
        //mLinearLayoutManager.setStackFromEnd(true);

        // db init
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFireBaseAdapter = new FirebaseRecyclerAdapter<State, StateHolder>(
                State.class,                                    // model
                //GV R.layout.state_item,                            // layout
                R.layout.state_card,
                StateHolder.class,                              // viewholder
                mFirebaseDatabaseReference.child(STATES)) {     // data
            @Override
            protected void populateViewHolder(StateHolder viewHolder, State model, final int position) {
                viewHolder.name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getFlagURL())
                        .placeholder(R.drawable.jc)
                        .resize(200,200)                // maintain
                        .centerInside()                 // aspect ratio
                        .into(viewHolder.flag);
            }
        };

        mFireBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount){
                super.onItemRangeInserted(positionStart, itemCount);
                int stateCount = mFireBaseAdapter.getItemCount();
                int lastVisiblePosition = mGridLayoutMangager.findLastCompletelyVisibleItemPosition();
                //GV int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (stateCount -1) && lastVisiblePosition == (positionStart -1))){
                    mStateRecyclerView.scrollToPosition(positionStart);
                }
                mStateRecyclerView.scrollToPosition(0);  // start at the top
            }
        });
        //GV mStateRecyclerView.setLayoutManager(mLinearLayoutManager);
        mStateRecyclerView.setLayoutManager(mGridLayoutMangager);
        mStateRecyclerView.setAdapter(mFireBaseAdapter);

        ItemClickSupport.addTo(mStateRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerview, int position, View v) {
                // Toast.makeText(v.getContext(), "You clicked on: " + position, Toast.LENGTH_SHORT).show();
                // State cState = mFireBaseAdapter.getItem(position);
                // ok - we can pass the item from the firebaseAdapter to the detailActivity :)
                Intent intent = new Intent(MainActivity.this, StateDetailActivity.class);
                intent.putExtra("STATE", mFireBaseAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
