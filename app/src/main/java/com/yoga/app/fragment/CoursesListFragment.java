package com.yoga.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.activities.MainActivity;
import com.yoga.app.adapter.CoursesListAdapter;
import com.yoga.app.base.APPFragmentManager;
import com.yoga.app.helper.YogaHelper;
import com.yoga.app.model.Course;
import com.yoga.app.model.CourseList;
import com.yoga.app.service.RetrofitInstance;
import com.yoga.app.utils.Prefs;
import com.yoga.app.utils.ProgressDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.yoga.app.constant.PrefConstants.ACCESS_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursesListFragment extends Fragment implements View.OnClickListener, CoursesListAdapter.Callback {

    FragmentActivity myContext;
    TextView mTitleTXT, mDescTXT;
    ImageView mBackArrowIMG, mBannerIMG;
    RecyclerView mRecycle;
    CoursesListAdapter mAdapter;
    ArrayList<Course> mData = new ArrayList();
    ProgressDialog aProgressDialog;
    private RetrofitInstance myRetrofitInstance;
    APPFragmentManager fragmentManager;

    public CoursesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses_list, container, false);

        init(view);
        listeners();
        setRecyclerView();
        getCourseList();

        return view;
    }

    private void init(View view) {
        myContext = getActivity();
        mTitleTXT = view.findViewById(R.id.title_txt);
        mDescTXT = view.findViewById(R.id.course_about_course_txt);
        mBackArrowIMG = view.findViewById(R.id.back_arrow);
        mBannerIMG = view.findViewById(R.id.course_banner_img);
        mRecycle = view.findViewById(R.id.main_course_video_list);

        fragmentManager = new APPFragmentManager(myContext);

        if (this.myRetrofitInstance == null) {
            myRetrofitInstance = new RetrofitInstance();
        }
        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).showBottomToolbar();

    }

    private void listeners() {
        mBackArrowIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.onBackPress();
            }
        });
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).hideToolbar();
        ((MainActivity) getActivity()).showBottomToolbar();
    }

    private void setRecyclerView() {
        mRecycle.setLayoutManager(new LinearLayoutManager(myContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CoursesListAdapter(getActivity(), mData, this);
        mRecycle.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void getCourseList() {
        aProgressDialog = new ProgressDialog(getActivity());
        aProgressDialog.show();

        Log.e("Id", YogaHelper.aDeviceId(getActivity()));

        Map<String, String> aHeaderMap = new HashMap<>();

        aHeaderMap.put("X-Device", YogaHelper.aDeviceId(getActivity()));
        //aHeaderMap.put("X-Localization", "en");
        aHeaderMap.put("Authorization", "Bearer " + Prefs.getString(ACCESS_TOKEN, ""));

        myRetrofitInstance.getAPI().getCourseList(aHeaderMap).enqueue(new Callback<CourseList>() {
            @Override
            public void onResponse(@NotNull Call<CourseList> call, @NotNull retrofit2.Response<CourseList> response) {
                aProgressDialog.dismiss();
                if (response != null) {
                    CourseList data = response.body();
                    if (data != null) {
                        if (data.getSuccess() == 1) {
                            mAdapter.update(data.getData().getData());
                            mDescTXT.setText(data.getData().getAbout());
                        }
                    } else {
                        YogaHelper.showAlertDialog(getActivity(), "Something went wrong");
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseList> call, Throwable t) {
                aProgressDialog.dismiss();
                //  Toast.makeText( getActivity(), "Something went wrong", Toast.LENGTH_SHORT ).show();
                YogaHelper.showAlertDialog(getActivity(), "Something went wrong");

            }
        });
    }

    @Override
    public void click(int aPostion, String s) {

    }
}
