package com.smartsafety.smartsafetyalarm.view;

import com.smartsafety.smartsafetyalarm.R;
import com.smartsafety.smartsafetyalarm.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends BaseActivity {
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		handler.postDelayed(run, 3000);
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
    }
	
	void appStart(){
		
		if( app.isLogin() ) {
			Intent intent = new Intent(this, ListActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		
	}
	
	Runnable run = new Runnable() {
		
		@Override
		public void run() {
			appStart();
		}
	};
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		
	}
}
