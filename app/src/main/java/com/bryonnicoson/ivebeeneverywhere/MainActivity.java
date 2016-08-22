package com.bryonnicoson.ivebeeneverywhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    // TODO: add dividers between items in RecyclerView ?
    // TODO: QUESTION: would it be better to create a local array of State objects - don't need it
    // TODO: QUESTION: in current backend-heavy/network-heavy (but persistent) method,
    // TODO:           would using a separate FirebaseHandler class be preferable
    // TODO: QUESTION: should I have just done this with SQLite like I've done before?
    // TODO: QUESTION: how to merge firebase tables
    // TODO: QUESTION: does offline persistence precede network call
    // TODO: NEXT STEPS: add firebase auth, user table, been-there table per each user,  (LEARN)
    // TODO: NEXT STEPS: add date picker and recyclerview checkbox/dates - connect to db

    // TODO: keep learning :)

    public static class StateHolder extends RecyclerView.ViewHolder {

        public ImageView flag;
        public TextView name;

        public StateHolder(View itemView){
            super(itemView);
            flag = (ImageView)itemView.findViewById(R.id.flag);
            name = (TextView)itemView.findViewById(R.id.name);
        }
    }

    public static final String STATES = "states";
    private RecyclerView mStateRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<State, StateHolder> mFireBaseAdapter;  // AWESOME !

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // view init
        mStateRecyclerView = (RecyclerView)findViewById(R.id.state_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this);
        //mLinearLayoutManager.setStackFromEnd(true);

        // db init
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFireBaseAdapter = new FirebaseRecyclerAdapter<State, StateHolder>(
                State.class,                                    // model
                R.layout.state_item,                            // layout
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
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (stateCount -1) && lastVisiblePosition == (positionStart -1))){
                    mStateRecyclerView.scrollToPosition(positionStart);
                }
                mStateRecyclerView.scrollToPosition(0);  // start at the top
            }
        });
        mStateRecyclerView.setLayoutManager(mLinearLayoutManager);
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
