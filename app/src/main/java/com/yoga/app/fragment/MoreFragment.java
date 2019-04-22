package com.yoga.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoga.app.R;
import com.yoga.app.adapter.MorePageAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


   View mView;
   private RecyclerView mMenuRecyclerView;
   private MorePageAdapter mMoreMenuListAdapter;
   private ArrayList<String> mMenuArrayList;

   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      mView = inflater.inflate( R.layout.fragment_more, container, false );

      init( mView );

      return mView;
   }

   private void init( View mView ) {
      mMenuRecyclerView = mView.findViewById( R.id.menu_RecyclerView );
      setRecyclerValue();
   }


   private void setRecyclerValue() {
      try {
         mMenuArrayList = new ArrayList<>();

         String[] aMenuTitles = getActivity().getResources().getStringArray( R.array.menu_items );
         mMenuArrayList.addAll( Arrays.asList( aMenuTitles ) );

         mMoreMenuListAdapter = new MorePageAdapter( getActivity(), mMenuArrayList, this );
         mMenuRecyclerView.setHasFixedSize( true );
         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getActivity() );
         mMenuRecyclerView.setLayoutManager( mLayoutManager );
         mMenuRecyclerView.setAdapter( mMoreMenuListAdapter );
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

}
