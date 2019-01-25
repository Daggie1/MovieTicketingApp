package com.franktan.popularmovies.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.model.HistoryModel;
import com.franktan.popularmovies.util.SimpleDividerItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private final String TAG = "ListScientistsFragment";
    private ArrayList<HistoryModel> mScientists;
    private ArrayList<String> mSelectedSymptomps;
    private RecyclerView mScientistRecyclerView;
    private ScientistAdapter mAdapter;

    DatabaseReference dbref;

    public HistoryActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbref= FirebaseDatabase.getInstance().getReference("History");
        setContentView(R.layout.activity_history);
        mScientists = new ArrayList<>();

        mScientistRecyclerView = (RecyclerView) findViewById(R.id.historyRexyxlerView);
        mScientistRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        mScientistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
     dbref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
             mScientists.clear();
             for (DataSnapshot ticketseSnapshot : dataSnapshot.getChildren()) {

                 HistoryModel historyList = ticketseSnapshot.getValue(HistoryModel.class);

                 mScientists.add(historyList);



             }
mAdapter.notifyDataSetChanged();

         }
         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
     });
    }
//recyclerviewOperations
private class ScientistHolder extends RecyclerView.ViewHolder{

    public TextView mNameTextView;


    public ScientistHolder(final View itemView){
        super(itemView);

        mNameTextView = (TextView) itemView.findViewById(R.id.textview_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
    private class ScientistAdapter extends RecyclerView.Adapter<ScientistHolder>{
        private ArrayList<HistoryModel> mScientists;
        public ScientistAdapter(ArrayList<HistoryModel> Scientists){
            mScientists = Scientists;
        }
        @Override
        public ScientistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HistoryActivity.this);
            View view = layoutInflater.inflate(R.layout.history_list_items,parent,false);
            return new ScientistHolder(view);
        }
        @Override
        public void onBindViewHolder(ScientistHolder holder, int position) {
            HistoryModel s = mScientists.get(position);

            holder.mNameTextView.setText(s.getmMovienAME());

        }
        @Override
        public int getItemCount() {
            return mScientists.size();
        }
    }

}
