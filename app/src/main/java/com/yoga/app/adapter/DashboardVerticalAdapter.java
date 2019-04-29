package com.yoga.app.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.fragment.DashboardFragment;
import com.yoga.app.model.Banner;
import com.yoga.app.model.Category;

import java.util.ArrayList;
import java.util.List;

public class DashboardVerticalAdapter extends RecyclerView.Adapter<DashboardVerticalAdapter.ViewHolder> {

   FragmentActivity mContext;
   ArrayList<String> mDashBoardHeading;
   ArrayList<Category> mCategory;

   public DashboardVerticalAdapter( FragmentActivity aContext, ArrayList<String> aDashBoardHeading,
                                    ArrayList<Category> aCategory ) {
      mContext = aContext;
      mDashBoardHeading = aDashBoardHeading;
      mCategory = aCategory;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i ) {
      View itemLayoutView = LayoutInflater.from( parent.getContext() )
              .inflate( R.layout.dashboard_vertical_layout, null );
      return new ViewHolder( itemLayoutView );

   }

   @Override
   public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {

      if( mCategory.size() > 0 ) {
         Category aCategory = mCategory.get( position );

         holder.mHeadingTextView.setText( aCategory.title );

         ArrayList<Category.Courses> aCourse = aCategory.mCoursesArrayList;

         HorizontalAdapter aHorizontalAdapter = new HorizontalAdapter( mContext, aCategory, aCourse, position );
         holder.mHorizontalRecyclerView.setLayoutManager( new LinearLayoutManager( mContext, LinearLayoutManager.HORIZONTAL, false ) );
         holder.mHorizontalRecyclerView.setAdapter( aHorizontalAdapter );
         holder.mHorizontalRecyclerView.setNestedScrollingEnabled( false );


         aHorizontalAdapter.notifyDataSetChanged();
      }
   }

   @Override
   public int getItemCount() {
      return mCategory.size();
   }

   public void updateAdapter( ArrayList<Category> aCategoryList ) {
      mCategory = aCategoryList;
      notifyDataSetChanged();
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

      TextView mHeadingTextView;
      RecyclerView mHorizontalRecyclerView;

      public ViewHolder( @NonNull View itemView ) {
         super( itemView );

         mHeadingTextView = itemView.findViewById( R.id.HeadingTitle );
         mHorizontalRecyclerView = itemView.findViewById( R.id.horizontal_recyclerView );

         final SnapHelper snapHelper = new PagerSnapHelper();
         snapHelper.attachToRecyclerView( mHorizontalRecyclerView );
      }
   }
}
