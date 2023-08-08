package com.example.user_login.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user_login.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private CourseAdapter adapter;
    private List<CourseModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        list=new ArrayList<>();
        list.add(new CourseModel(R.drawable.computer_ic,"Computer Engineering","Computer engineering (CoE or CpE) is a branch of electrical engineering and computer science that integrates several fields of computer science and electronic engineering required to develop computer hardware and software."));
        list.add(new CourseModel(R.drawable.it_ic,"Information Technology","the study or use of electronic equipment, especially computers, for collecting, storing and sending out information."));
        list.add(new CourseModel(R.drawable.entc_ic,"Electronics And Telecomunication","This field comprises of the combination of electronics and communication applications. Electronics and telecommunication engineering is designing, research and verifying the electronic systems, they also look into the concept of broadcasting signals."));
        list.add(new CourseModel(R.drawable.mechanical_ic,"Mechanical Engineering","Mechanical engineering is one of the broadest engineering disciplines. Mechanical engineers design, develop, build, and test. They deal with anything that moves, from components to machines to the human body."));
        list.add(new CourseModel(R.drawable.civil_ic,"Civil Engineering","Civil engineering is a professional engineering discipline that deals with the design, construction, and maintenance of the physical and naturally built ."));

        adapter=new CourseAdapter(getContext(),list);
        viewPager=view.findViewById(R.id.cources);
        viewPager.setAdapter(adapter);

        ImageView imageView=view.findViewById(R.id.collageimage);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/sae-coe.appspot.com/o/gallery%2F%5BB%40b056401jpg?alt=media&token=3dfbf17e-4568-4412-a705-1884dfd19543").into(imageView);
        return view;
    }

}