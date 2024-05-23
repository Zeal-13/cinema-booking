package com.example.cinema;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllFilmsFragment extends Fragment {

    TextView textView;
    DatabaseReference databaseReference;

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

        // Add a ValueEventListener to the databaseReference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the textView
                textView.setText("");
                // Iterate through the film data in the dataSnapshot
                for (DataSnapshot filmSnapshot : dataSnapshot.getChildren()) {
                    // Get the film data from the filmSnapshot
                    Film film = filmSnapshot.getValue(Film.class);
                    // Append the film data to the textView
                    textView.append(film.getName() + "\n" +
                            film.getDuration() + "\n" +
                            film.getPrice() + "\n" +
                            film.getGenre() + "\n\n");
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
