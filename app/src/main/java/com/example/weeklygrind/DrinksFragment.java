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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class DrinksFragment extends Fragment{

    private RecyclerView drinkList;
    private FirebaseFirestore fireStore;
    private CollectionReference appref;
    private FirestoreRecyclerAdapter adapter;
    String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        drinkList = view.findViewById(R.id.drinkList);
        fireStore = FirebaseFirestore.getInstance();



        appref = fireStore.collection("Items");
        Query query = appref.whereEqualTo("category", "NxYn8dYncO9O6s7KZ2PU");

        FirestoreRecyclerOptions<AppeBlock> options = new FirestoreRecyclerOptions.Builder<AppeBlock>()
                .setQuery(query, AppeBlock.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<AppeBlock, AppeViewHolder>(options) {
            @NonNull
            @Override
            public AppeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                return new DrinksFragment.AppeViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull AppeViewHolder holder, int position, @NonNull AppeBlock model) {
                holder.listItem.setText(model.getItem());
                holder.listPrice.setText("$" + model.getPrice());
            }
        };

        drinkList.setHasFixedSize(true);
        drinkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        drinkList.setAdapter(adapter);

        return view;
    }



    private class AppeViewHolder extends RecyclerView.ViewHolder{
        private TextView listItem, listPrice;
        public AppeViewHolder(@NonNull View itemView) {
            super(itemView);

            listItem = itemView.findViewById(R.id.showItem);
            listPrice = itemView.findViewById(R.id.showPrice);
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