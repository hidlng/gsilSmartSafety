package com.smartsafety.smartsafetyalarm.view;

import com.google.android.gcm.GCMRegistrar;
import com.smartsafety.smartsafetyalarm.R;
import com.smartsafety.smartsafetyalarm.util.BackPressCloseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("JavascriptInterface")
public class MainActivity extends Activity {
	String regId = "";
	WebView mWeb = null;
	Context app = null;
	private BackPressCloseHandler backPressCloseHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerGcm();
		app = this;
		backPressCloseHandler = new BackPressCloseHandler(this);
		
		//Web view Setting
		mWeb = (WebView) this.findViewById(R.id.webview);
		mWeb.setWebViewClient(new WebViewClient());

		WebSettings set = mWeb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		set.setUseWideViewPort(true);
		mWeb.setInitialScale(1);
		
		mWeb.loadUrl("http://54.65.130.238:8080/SmartSafety/login");
		
		mWeb.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mWeb.loadUrl("javascript:setPData('"+regId+"')");
				return false;
			}
		});
		
		mWeb.setWebChromeClient(new WebChromeClient() {
            public boolean onJsAlert(WebView view, String url,
                    String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(app)
                        .setTitle("Test")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        result.confirm();
                                    }
                                }).setCancelable(false).create().show();

                return true;
         }});
		
		
		
	}
	
	public void registerGcm() {
		 
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		 
		regId = GCMRegistrar.getRegistrationId(this);
		 
		if (regId.equals("")) {
			GCMRegistrar.register(this, "384632098747" );
//			384632098747
		} else {
			Log.e("id", regId);
			
		}
		 
	}
	
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
    
    public class JavaScriptInterface {
        Context mContext;
 
        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }
        
        @JavascriptInterface
        public void getMarketData( String value ) {

        }
    }

}
