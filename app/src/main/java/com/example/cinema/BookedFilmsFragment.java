package com.example.cinema;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookedFilmsFragment extends Fragment {

    private LinearLayout linearLayout;

    public BookedFilmsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booked_films, container, false);
        linearLayout = view.findViewById(R.id.linearLayout);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference filmsRef = database.getReference("all");

        filmsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                linearLayout.removeAllViews(); // Clear the linearLayout
                for (DataSnapshot filmSnapshot : dataSnapshot.getChildren()) {
                    boolean isBooked = Boolean.TRUE.equals(filmSnapshot.child("booked")
                            .getValue(Boolean.class));
                    if (isBooked) {

                        Film film = filmSnapshot.getValue(Film.class);

                        // Inflate the film_item layout
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        View filmView = inflater.inflate(R.layout.film_item, linearLayout, false);

                        // Set the film information
                        TextView textView = filmView.findViewById(R.id.textView);
                        String filmInfo = film.getName() + " - " + film.getDuration() + " - " +
                                film.getPrice() + " - " + film.getGenre();
                        textView.setText(filmInfo);

                        Button button = filmView.findViewById(R.id.button);
                        button.setText("Unbook");
                        button.setOnClickListener(v -> {
                            // Unbook the film
                            filmSnapshot.getRef().child("booked").setValue(false);
                        });

                        // Add the film view to the linear layout
                        linearLayout.addView(filmView);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).showBottomNavigationView(true);
    }
}