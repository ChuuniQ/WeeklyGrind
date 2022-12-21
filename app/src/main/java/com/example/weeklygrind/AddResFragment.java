package com.example.weeklygrind;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddResFragment extends Fragment implements View.OnClickListener {

    private DatePickerDialog datePickerDialog;
    private Button dateButton, timeButton, confButton;
    private EditText paxNo;
    private int hour, minute;

    private FirebaseAuth fireAuth;
    private FirebaseFirestore fireStore;
    private String UID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_res, container, false);

        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();


        initDatePicker();
        dateButton = view.findViewById(R.id.datePickerBtn);
        timeButton = view.findViewById(R.id.timePickerBtn);
        confButton = view.findViewById(R.id.confBtn);
        paxNo = view.findViewById(R.id.paxNumber);
        confButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);


        return view;
    }

    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                String date = makeDateString(day,month,year);
                dateButton.setText(date);



            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.datePickerBtn:
                 datePickerDialog.show();
                 break;

            case R.id.timePickerBtn:
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    hour = selectedHour;
                    minute = selectedMinute;
                    timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour,minute));

                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, true);
                timePickerDialog.show();
                break;
            case R.id.confBtn:

                String date = dateButton.getText().toString();
                String time = timeButton.getText().toString();
                String pax = paxNo.getText().toString();

                UID = fireAuth.getCurrentUser().getUid();
                Map<String, Object> reserve = new HashMap<>();
                reserve.put("Date", date);
                reserve.put("Time", time);
                reserve.put("PAX", pax);
                reserve.put("UserID", UID);

                fireStore.collection("Reservations").add(reserve).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Reservation successful.", Toast.LENGTH_SHORT).show();
                        Log.d("tag","Reservation successful.");
                        dateButton.setText("JAN 01 2021");
                        timeButton.setText("00:00");
                        paxNo.setText("");


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Reservation not successful.", Toast.LENGTH_SHORT).show();
                        Log.d("tag","Reservation not successful.");
                        dateButton.setText("JAN 01 2021");
                        timeButton.setText("00:00");
                        paxNo.setText("3");

                    }
                });




        }
    }



}