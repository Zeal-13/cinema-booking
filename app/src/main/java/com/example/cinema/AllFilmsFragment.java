package com.example.cinema;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllFilmsFragment extends Fragment {

    TextView textView;
    DatabaseReference databaseReference;
    private LinearLayout linearLayout;

    public AllFilmsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the databaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference("all");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceData) {
        View view = inflater.inflate(R.layout.fragment_all_films, container, false);
        textView = view.findViewById(R.id.textView);
        linearLayout = view.findViewById(R.id.linearLayout);

        // Add a ValueEventListener to the databaseReference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the linearLayout
                linearLayout.removeAllViews();
                // Iterate through the film data in the dataSnapshot
                for (DataSnapshot filmSnapshot : dataSnapshot.getChildren()) {
                    // Get the film data from the filmSnapshot
                    Film film = filmSnapshot.getValue(Film.class);
                    // Inflate the layout for each film
                    View filmView = getLayoutInflater().inflate(R.layout.film_item, null);
                    TextView textView = filmView.findViewById(R.id.textView);
                    textView.setText(film.getName() + "\n" +
                            film.getDuration() + "\n" +
                            film.getPrice() + "\n" +
                            film.getGenre() + "\n\n");
                    Button button = filmView.findViewById(R.id.button);
                    //set the OnClickListener for the button
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Check if the film is already booked
                            if (film.isBooked()) {
                                Toast.makeText(getContext(), "Already booked", Toast.LENGTH_SHORT).show();
                            } else {
                                // Update the value of isBooked in the database
                                filmSnapshot.getRef().child("booked").setValue(true);
                                Toast.makeText(getContext(), "Booked", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    // Add the layout to the linearLayout
                    linearLayout.addView(filmView);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("AllFilmsFragment", "onCancelled", databaseError.toException());
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
