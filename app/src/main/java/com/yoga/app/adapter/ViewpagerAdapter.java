package com.yoga.app.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoga.app.R;
import com.yoga.app.model.Banner;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends PagerAdapter {

   private FragmentActivity activity;

   List<Banner> mBannerList;


   public ViewpagerAdapter( FragmentActivity aActivity, List<Banner> aBannerList ) {
      mBannerList = aBannerList;
      activity = aActivity;
   }

   @Override
   public Object instantiateItem( ViewGroup container, int position ) {
      LayoutInflater inflater = ( ( Activity ) activity ).getLayoutInflater();
      View viewItem = inflater.inflate( R.layout.pager_item, container, false );

      ImageView imageView = viewItem.findViewById( R.id.pager_image );
      // mBannerList.get(position)

      Banner aBanner = mBannerList.get( position );

      if( aBanner.banner_file_type.contains( "image" ) ) {
         Picasso.with( activity ).load( mBannerList.get( position ).banner_file ).placeholder( R.drawable.banner ).into( imageView );
      } else if( aBanner.banner_file_type.contains( "video" ) ) {
         Picasso.with( activity ).load( mBannerList.get( position ).banner_thumbnail ).placeholder( R.drawable.banner ).into( imageView );
      }

      ( ( ViewPager ) container ).addView( viewItem );

      return viewItem;
   }

   @Override
   public int getCount() {
      // TODO Auto-generated method stub
      return mBannerList.size();
   }

   @Override
   public boolean isViewFromObject( View view, Object object ) {
      // TODO Auto-generated method stub
      return view == ( ( View ) object );
   }

   @Override
   public void destroyItem( ViewGroup container, int position, Object object ) {
      // TODO Auto-generated method stub
      ( ( ViewPager ) container ).removeView( ( View ) object );
   }

   public void updateAdapter( ArrayList<Banner> aBannerList ) {
      mBannerList = aBannerList;
      this.notifyDataSetChanged();
   }
}
