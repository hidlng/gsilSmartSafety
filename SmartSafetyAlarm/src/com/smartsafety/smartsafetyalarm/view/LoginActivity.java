package com.smartsafety.smartsafetyalarm.view;


import com.google.android.gcm.GCMRegistrar;
import com.smartsafety.smartsafetyalarm.R;
import com.smartsafety.smartsafetyalarm.base.BaseActivity;
import com.smartsafety.smartsafetyalarm.model.Work;
import com.smartsafety.smartsafetyalarm.util.BackPressCloseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {
	String regId = "";

	
	
	EditText loginuseridTxt = null;
	EditText loginuserpwTxt = null;
	Button loginBtn = null;
	
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		registerGcm();
		backPressCloseHandler = new BackPressCloseHandler(this);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		loginuseridTxt = ( EditText ) findViewById(R.id.loginuseridTxt);
		loginuserpwTxt = ( EditText ) findViewById(R.id.loginuserpwTxt);
		
		loginBtn = ( Button ) findViewById(R.id.loginBtn);
		
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eventLogin();
			}
		});
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void eventLogin() {
		String id = "";
		String password = "";
		
		if( !loginuseridTxt.getText().toString().equals("") ) {
			id = loginuseridTxt.getText().toString();
		} else {
			showToast("아이디를 입력해 주세요.");
			return;
		}
		
		if( !loginuserpwTxt.getText().toString().equals("") ) {
			password = loginuserpwTxt.getText().toString();
		} else {
			showToast("비밀번호를 입력해 주세요.");
			return;
		}
		
		eventStartLogin( id, password );
	}
	
	private void eventStartLogin( final String id, final String password ) {
		pShow();
		startThread(new Runnable() {
			public void run() {
				final Work work;
				work = api.login(getBaseContext(), id, password ,regId);
				runOnUiThread(new Runnable() {
					public void run() {
						if(  work != null ) {
							app.setLogin(true);
							app.setAutoLogin(true);
							app.setUseridx(work.getUseridx());
							app.setSiteidx(work.getSiteidx());
							
							eventUpdateSignUpComplete();
						} else {
							showToast("로그인에 실패하였습니다.");
						}
						pHide();
					}
				});
			}
		});
	}
	
	public void eventUpdateSignUpComplete() {
		showToast("환영합니다.");
		Intent intent = new Intent(getApplicationContext(), ListActivity.class);
		startActivity(intent);
		finish();
		
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

}
