package com.example.weeklygrind;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class ViewOrderFragment extends Fragment implements View.OnClickListener {

    private TextView ordersList;
    private Button showOrderBtn;
    private FirebaseFirestore fireStore;
    private FirebaseAuth fireAuth;
    private CollectionReference appref;
    private String UID;
    private CollectionReference orderref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_order, container, false);

        ordersList = view.findViewById(R.id.ordersList);
        showOrderBtn = view.findViewById(R.id.showOrderBtn);
        showOrderBtn.setOnClickListener(this);

        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        orderref = fireStore.collection("Orders");
        UID = fireAuth.getCurrentUser().getUid();


        return view;
    }


    @Override
    public void onClick(View view) {
        orderref.whereEqualTo("UserID",UID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            OrderBlock orderBlock = documentSnapshot.toObject(OrderBlock.class);

                            data += "Your Orders:";

                            for (String ord : orderBlock.getOrderList()){
                                data += "\n-" + ord;
                            }
                            data += "\n\n";
                        }
                        ordersList.setText(data);
                    }
                });
    }
}