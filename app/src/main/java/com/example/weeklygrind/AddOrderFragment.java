package com.example.weeklygrind;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddOrderFragment extends Fragment implements View.OnClickListener {

    private CheckBox burger, cheesestick, chickenwings, lasgna, griilledsalmon, fishchips;
    private CheckBox lechflan, chocowaffle, coffeejelly, coffee, cocacola, lemontea;
    private Button orderbtn;
    private ArrayList<String> orderList;
    private String[] orderString;
    private FirebaseFirestore fireStore;
    private FirebaseAuth fireAuth;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_order, container, false);

        fireStore = FirebaseFirestore.getInstance();
        fireAuth = FirebaseAuth.getInstance();

        burger = view.findViewById(R.id.burger);
        cheesestick = view.findViewById(R.id.cheesestick);
        chickenwings = view.findViewById(R.id.chickenwings);
        griilledsalmon = view.findViewById(R.id.grilledsalmon);
        fishchips = view.findViewById(R.id.fishchips);
        lasgna = view.findViewById(R.id.lasagna);
        lechflan = view.findViewById(R.id.lechflan);
        chocowaffle = view.findViewById(R.id.chocowaffle);
        coffeejelly = view.findViewById(R.id.coffeejelly);
        coffee = view.findViewById(R.id.coffee);
        cocacola = view.findViewById(R.id.cocacola);
        lemontea = view.findViewById(R.id.lemontea);
        orderbtn = view.findViewById(R.id.orderBtn);

        burger.setOnClickListener(this);
        cheesestick.setOnClickListener(this);
        chickenwings.setOnClickListener(this);
        griilledsalmon.setOnClickListener(this);
        fishchips.setOnClickListener(this);
        lasgna.setOnClickListener(this);
        lechflan.setOnClickListener(this);
        chocowaffle.setOnClickListener(this);
        coffeejelly.setOnClickListener(this);
        coffee.setOnClickListener(this);
        cocacola.setOnClickListener(this);
        lemontea.setOnClickListener(this);
        orderbtn.setOnClickListener(this);

        orderList = new ArrayList<>();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.orderBtn:

                if (burger.isChecked()){
                    orderList.add("Burger Sliders");
                }else{
                    orderList.remove("Burger Sliders");
                }

                if (cheesestick.isChecked()){
                    orderList.add("Cheese Sticks");
                }else{
                    orderList.remove("Cheese Sticks");
                }

                if (chickenwings.isChecked()){
                    orderList.add("Chicken Wings");
                }else{
                    orderList.remove("Chicken Wings");
                }

                if (lasgna.isChecked()){
                    orderList.add("Lasagna");
                }else{
                    orderList.remove("Lasagna");
                }

                if (griilledsalmon.isChecked()){
                    orderList.add("Grilled Salmon");
                }else{
                    orderList.remove("Grilled Salmon");
                }

                if (fishchips.isChecked()){
                    orderList.add("Fish and Chips");
                }else{
                    orderList.remove("Fish and Chips");
                }

                if (lechflan.isChecked()){
                    orderList.add("Leche-Flan");
                }else{
                    orderList.remove("Leche-Flan");
                }

                if (chocowaffle.isChecked()){
                    orderList.add("Chocolate Waffle");
                }else{
                    orderList.remove("Chocolate Waffle");
                }

                if (coffeejelly.isChecked()){
                    orderList.add("Coffee Jelly");
                }else{
                    orderList.remove("Coffee Jelly");
                }

                if (coffee.isChecked()){
                    orderList.add("Coffee");
                }else{
                    orderList.remove("Coffee");
                }

                if (cocacola.isChecked()){
                    orderList.add("Coca Cola");
                }else{
                    orderList.remove("Coca Cola");
                }

                if (lemontea.isChecked()){
                    orderList.add("Iced Lemon Tea");
                }else{
                    orderList.remove("Iced Lemon Tea");
                }

                orderString = new String[orderList.size()-1];
                orderString = orderList.toArray(orderString);


                UID = fireAuth.getCurrentUser().getUid();
                Map<String, Object> oList = new HashMap<>();
                oList.put("OrderList", Arrays.asList(orderString));
                oList.put("UserID", UID);
                fireStore.collection("Orders").add(oList).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Added to Orders.", Toast.LENGTH_SHORT).show();
                        Log.d("tag","Order successful.");
                        clearCheck();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Order failed.", Toast.LENGTH_SHORT).show();
                        Log.d("tag","Order unsuccessful.");
                        clearCheck();
                    }
                });

                clearCheck();

                break;
        }
    }

    public void clearCheck(){
        burger.setChecked(false);
        cheesestick.setChecked(false);
        chickenwings.setChecked(false);
        lasgna.setChecked(false);
        griilledsalmon.setChecked(false);
        fishchips.setChecked(false);
        lechflan.setChecked(false);
        chocowaffle.setChecked(false);
        coffeejelly.setChecked(false);
        coffee.setChecked(false);
        cocacola.setChecked(false);
        lemontea.setChecked(false);
    }
}