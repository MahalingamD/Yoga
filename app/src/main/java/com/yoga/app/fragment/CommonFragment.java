package com.yoga.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yoga.app.R;
import com.yoga.app.model.Pages;
import com.yoga.app.utils.Prefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends Fragment {


   WebView mWebView;
   View mView;


   @Override
   public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState ) {
      // Inflate the layout for this fragment
      mView = inflater.inflate( R.layout.fragment_common, container, false );

      init( mView );

      return mView;
   }

   private void init( View mView ) {
      mWebView = mView.findViewById( R.id.webView );


      WebSettings webSettings = mWebView.getSettings();
      webSettings.setJavaScriptEnabled( true );

      getBundleValue();


   }

   private void getBundleValue() {

      Bundle aBundle = getArguments();
      String aString = aBundle.getString( "page_value", "" );

      Pages aPages = Prefs.getObject( "pages", Pages.class );
      String aPageURL;

      switch( aString ) {
         case "about":
            aPageURL = aPages.about;
            break;
         case "terms":
            aPageURL = aPages.terms;
            break;
         default:
            aPageURL = aPages.privacy;
            break;
      }

      mWebView.loadUrl( aPageURL );
   }

}
