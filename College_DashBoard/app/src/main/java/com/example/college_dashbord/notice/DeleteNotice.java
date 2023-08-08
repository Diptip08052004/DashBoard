package com.example.college_dashbord.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.college_dashbord.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteNotice extends AppCompatActivity {

    private RecyclerView deleteNoticeRecycle;
    private ProgressBar progressBar;
    private ArrayList<NoticeData>list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        deleteNoticeRecycle=findViewById(R.id.deleteNoticeRecycle);
        progressBar=findViewById(R.id.progressbar);
        reference= FirebaseDatabase.getInstance().getReference().child("Notice");

        deleteNoticeRecycle.setLayoutManager(new LinearLayoutManager(this));
        deleteNoticeRecycle.setHasFixedSize(true);

        getNotice();
    }

    private void getNotice() {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list=new ArrayList<>();
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        NoticeData data=snapshot1.getValue(NoticeData.class);
                        list.add(data);
                    }
                    adapter=new NoticeAdapter(DeleteNotice.this,list);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    deleteNoticeRecycle.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(DeleteNotice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

}