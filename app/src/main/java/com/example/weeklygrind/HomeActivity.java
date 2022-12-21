package com.example.weeklygrind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeActivity extends AppCompatActivity {

    private TextView name,email;
    private FirebaseAuth fireAuth;
    private FirebaseFirestore fireStore;
    private String userId,fname,lname;
    private NavigationView navView;
    private View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navView = (NavigationView)findViewById(R.id.navigation_view);
        header = navView.getHeaderView(0);

        name = header.findViewById(R.id.headerName);
        email = header.findViewById(R.id.headerEmail);
        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        userId = fireAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fireStore.collection("Users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    fname = documentSnapshot.getString("First Name");
                    lname = documentSnapshot.getString("Last Name");
                    name.setText(fname + " " + lname);
                    email.setText(documentSnapshot.getString("Email"));

                } else {
                    Log.d("tag", "get failed with ", task.getException());
                }
            }
        });

        MaterialToolbar toolbar = findViewById(R.id.topAppbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        replaceFragment(new MenuFragment());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);

                switch(id){

                    case R.id.nav_reservations:
                        replaceFragment(new ReservationFragment());
                        break;
                    case R.id.nav_order:
                        replaceFragment(new OrderFragment());
                        break;
                    case R.id.nav_menu:
                        replaceFragment(new MenuFragment());
                        break;
                    case R.id.nav_favorites:
                        replaceFragment(new FavoritesFragment());
                        break;
                    case R.id.nav_logout:
                        toLogout();
                    default:
                        return true;
                }

                return true;
            }
        });

    }

    private void toLogout(){

        fireAuth.signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();

    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

}