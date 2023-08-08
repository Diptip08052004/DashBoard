package com.example.user_login.ui.gallary;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GallaryFragment extends Fragment {

    RecyclerView eventrecycle,daysrecycle,otherrecycle;
    GallaryAdapter adapter;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_gallary, container, false);
        eventrecycle=view.findViewById(R.id.eventrecycle);
        daysrecycle=view.findViewById(R.id.daysrecycle);
        otherrecycle=view.findViewById(R.id.otherrecycle);
        reference= FirebaseDatabase.getInstance().getReference().child("gallery");

        geteventimage();
        getdaysimage();
        getotherimage();

        return view;
    }

    private void getotherimage() {
        reference.child("Other Event").addValueEventListener(new ValueEventListener() {
            List<String> imagelist=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        String data= (String) snapshot1.getValue();
                        imagelist.add(data);
                    }
                    adapter = new GallaryAdapter(getContext(),imagelist);
                    otherrecycle.setLayoutManager(new GridLayoutManager(getContext(),3));

                    otherrecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getdaysimage() {
        reference.child("Collage Days").addValueEventListener(new ValueEventListener() {
            List<String> imagelist=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    String data= (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter = new GallaryAdapter(getContext(),imagelist);
                daysrecycle.setLayoutManager(new GridLayoutManager(getContext(),3));
                daysrecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void geteventimage() {
        reference.child("National event").addValueEventListener(new ValueEventListener() {
            List<String> imagelist=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    String data= (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter = new GallaryAdapter(getContext(),imagelist);
                eventrecycle.setLayoutManager(new GridLayoutManager(getContext(),3));
                eventrecycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}