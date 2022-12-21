package com.example.weeklygrind;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ViewRateFragment extends Fragment{

    private RecyclerView rateList;
    private FirebaseFirestore fireStore;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_rate, container, false);

        rateList = view.findViewById(R.id.rateList);
        fireStore = FirebaseFirestore.getInstance();




        Query query = fireStore.collection("Ratings");

        FirestoreRecyclerOptions<RateBlock> options = new FirestoreRecyclerOptions.Builder<RateBlock>()
                .setQuery(query, RateBlock.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<RateBlock, RateViewHolder>(options) {
            @NonNull
            @Override
            public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_layout, parent, false);
                return new ViewRateFragment.RateViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RateViewHolder holder, int position, @NonNull RateBlock model) {
                holder.showNick.setText(model.getNickname()+"'s rating:");
                holder.showRate.setText(model.getRating());
                holder.showCom.setText(model.getComment());
            }
        };

        rateList.setHasFixedSize(true);
        rateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rateList.setAdapter(adapter);

        return view;
    }



    private class RateViewHolder extends RecyclerView.ViewHolder{
        private TextView showNick, showRate, showCom;
        public RateViewHolder(@NonNull View itemView) {
            super(itemView);

            showNick = itemView.findViewById(R.id.nickShow);
            showRate = itemView.findViewById(R.id.rateShow);
            showCom = itemView.findViewById(R.id.comShow);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}