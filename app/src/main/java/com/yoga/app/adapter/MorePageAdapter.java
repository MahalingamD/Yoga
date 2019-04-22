package com.yoga.app.adapter;

import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoga.app.R;
import com.yoga.app.fragment.MoreFragment;

import java.util.ArrayList;

public class MorePageAdapter extends RecyclerView.Adapter<MorePageAdapter.ViewHolder> {

   FragmentActivity mContext;
   ArrayList<String> mMenuArrayList;
   private ArrayList<Integer> myIcons;

   public MorePageAdapter( FragmentActivity aContext, ArrayList<String> aMenuArrayList, MoreFragment moreFragment1 ) {
      mContext = aContext;
      mMenuArrayList = aMenuArrayList;

      TypedArray aIcons = mContext.getResources().obtainTypedArray( R.array.menu_icon_normal );
      myIcons = new ArrayList<>();
      for( int i = 0; i < aIcons.length(); i++ ) {
         myIcons.add( aIcons.getResourceId( i, -1 ) );
      }
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int i ) {
      View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.navigation_menu_item, parent, false );
      return new ViewHolder( itemView );
   }

   @Override
   public void onBindViewHolder( @NonNull ViewHolder viewHolder, int i ) {

      viewHolder.mTitleText.setText( mMenuArrayList.get( i ) );
      viewHolder.mMenuImage.setImageResource( myIcons.get( i ) );

   }

   @Override
   public int getItemCount() {
      return mMenuArrayList.size();
   }

   class ViewHolder extends RecyclerView.ViewHolder {

      TextView mTitleText;
      ImageView mMenuImage;

      ViewHolder( @NonNull View itemView ) {
         super( itemView );

         mTitleText = itemView.findViewById( R.id.inflate_nav_menu_title );
         mMenuImage = itemView.findViewById( R.id.inflate_nav_menu_image );
      }
   }


   public interface Callback {
      void click( int aPostion );
   }
}
