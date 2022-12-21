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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewResFragment extends Fragment {

    private RecyclerView reslist;
    private FirebaseAuth fireAuth;
    private FirebaseFirestore fireStore;
    private String UID;
    private FirestoreRecyclerAdapter adapter;
    private CollectionReference resref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_res, container, false);

        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        reslist = view.findViewById(R.id.resList);

        UID = fireAuth.getCurrentUser().getUid();
        resref = fireStore.collection("Reservations");
        Query query = resref.whereEqualTo("UserID",UID);

        FirestoreRecyclerOptions<ResBlock> options = new FirestoreRecyclerOptions.Builder<ResBlock>()
                .setQuery(query, ResBlock.class).build();

        adapter = new FirestoreRecyclerAdapter<ResBlock, ResViewHolder>(options) {
            @NonNull
            @Override
            public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resblock_layout, parent, false);
                return new ResViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ResViewHolder holder, int position, @NonNull ResBlock model) {


                holder.listDate.setText(model.getDate());
                holder.listTime.setText(model.getTime());
                holder.listPax.setText(model.getPAX());
            }
        };

        reslist.setHasFixedSize(true);
        reslist.setLayoutManager(new LinearLayoutManager(getActivity()));
        reslist.setAdapter(adapter);


        return view;
    }

    private class ResViewHolder extends RecyclerView.ViewHolder{

        private TextView listDate, listTime, listPax;

        public ResViewHolder(@NonNull View itemView) {
            super(itemView);

            listDate = itemView.findViewById(R.id.showDate);
            listTime = itemView.findViewById(R.id.showTime);
            listPax = itemView.findViewById(R.id.showPAX);
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