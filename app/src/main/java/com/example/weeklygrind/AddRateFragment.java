package com.example.weeklygrind;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRateFragment extends Fragment implements View.OnClickListener {

    private EditText userNickname, userFeedback;
    private RatingBar ratingBar;
    private Button rateBtn;
    private FirebaseFirestore fireStore;
    private String rating,nname,feed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_rate, container, false);

        fireStore = FirebaseFirestore.getInstance();
        userNickname = view.findViewById(R.id.userNickname);
        userFeedback = view.findViewById(R.id.userFeedback);
        ratingBar = view.findViewById(R.id.ratingBar);
        rateBtn = view.findViewById(R.id.rateBtn);
        rateBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        nname = userNickname.getText().toString();
        feed = userFeedback.getText().toString();
        rating = String.valueOf(ratingBar.getRating());

        Map<String, Object> ratingcom = new HashMap<>();

        if (TextUtils.isEmpty(nname)){
            ratingcom.put("Nickname", "Anonymous");
        }else{
            ratingcom.put("Nickname", nname);
        }
        ratingcom.put("Rating", rating);
        ratingcom.put("Comment",feed);

        fireStore.collection("Ratings").add(ratingcom).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "Thank you!", Toast.LENGTH_SHORT).show();
                Log.d("tag","Rating successful.");
                userNickname.setText("");
                userFeedback.setText("");
                ratingBar.setRating(0F);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
                Log.d("tag","Rating not successful.");
                userNickname.setText("");
                userFeedback.setText("");
                ratingBar.setRating(0F);

            }
        });



    }
}