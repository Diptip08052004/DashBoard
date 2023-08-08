package com.example.user_login.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.user_login.R;

import java.util.List;

public class CourseAdapter extends PagerAdapter {
    private Context context;
    private List<CourseModel>list;

    public CourseAdapter(Context context, List<CourseModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.courseitem,container,false);

        ImageView course_icon;
        TextView course_title,course_des;

        course_icon=view.findViewById(R.id.comp_icon);
        course_title=view.findViewById(R.id.comp_title);
        course_des=view.findViewById(R.id.comp_description);

        course_icon.setImageResource(list.get(position).getImg());
        course_title.setText(list.get(position).getTitle());
        course_des.setText(list.get(position).getDescription());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
