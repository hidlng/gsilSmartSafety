package com.smartsafety.smartsafetyalarm.view;

import java.io.UnsupportedEncodingException;

import com.smartsafety.smartsafetyalarm.R;
import com.smartsafety.smartsafetyalarm.base.BaseActivity;





import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailActivity extends BaseActivity {

	RelativeLayout menuBar = null;
	
	LinearLayout check1 = null;
	LinearLayout check2 = null;
	LinearLayout check3 = null;
	LinearLayout check4 = null;
	
	ImageView chk1 = null;
	ImageView chk2 = null;
	ImageView chk3 = null;
	ImageView chk4 = null;
	
	int currentClick1 = 0;
	int currentClick2 = 0;
	int currentClick3 = 0;
	int currentClick4 = 0;
	
	Button checkCompleteBtn = null;
	
	TextView workTtitleText = null;
	
	String workidx ="";
	String worktitle = "";	
	String checkyn = "";
	String workdate = "";
	String result = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		init();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		if( getIntent().getStringExtra("workidx") != null && !getIntent().getStringExtra("workidx").equals("")) {
			workidx = getIntent().getStringExtra("workidx");
		} else {
			workidx = "";
		}
		
		if( getIntent().getStringExtra("worktitle") != null && !getIntent().getStringExtra("worktitle").equals("")) {
			worktitle = getIntent().getStringExtra("worktitle");
		} else {
			worktitle = "";
		}
		
		if( getIntent().getStringExtra("checkyn") != null && !getIntent().getStringExtra("checkyn").equals("")) {
			checkyn = getIntent().getStringExtra("checkyn");
		} else {
			checkyn = "";
		}
		
		
		if( getIntent().getStringExtra("workdate") != null && !getIntent().getStringExtra("workdate").equals("")) {
			workdate = getIntent().getStringExtra("workdate");
		} else {
			workdate = "";
		}
		
		
		
		menuBar = ( RelativeLayout ) findViewById(R.id.menuBar);
		menuBar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		check1 = ( LinearLayout ) findViewById(R.id.check1);

		check2 = ( LinearLayout ) findViewById(R.id.check2);
		check3 = ( LinearLayout ) findViewById(R.id.check3);
		check4 = ( LinearLayout ) findViewById(R.id.check4);
		
		chk1 = ( ImageView ) findViewById(R.id.chk1);
		chk2 = ( ImageView ) findViewById(R.id.chk2);
		chk3 = ( ImageView ) findViewById(R.id.chk3);
		chk4 = ( ImageView ) findViewById(R.id.chk4);
		
		checkCompleteBtn = ( Button ) findViewById(R.id.checkCompleteBtn);
		
		workTtitleText = ( TextView ) findViewById(R.id.workTtitleText);
		
		workTtitleText.setText(worktitle);
		
		check1.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            	if( currentClick1 == 0 ) {
            		chk1.setImageResource(R.drawable.check_c);
            		currentClick1 = 1;
            	} else {
            		chk1.setImageResource(R.drawable.check);
            		currentClick1 = 0;
            	}
			}
		});
		
		check2.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            	if( currentClick2 == 0 ) {
            		chk2.setImageResource(R.drawable.check_c);
            		currentClick2 = 1;
            	} else {
            		chk2.setImageResource(R.drawable.check);
            		currentClick2 = 0;
            	}
			}
		});
		
		check3.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            	if( currentClick3 == 0 ) {
            		chk3.setImageResource(R.drawable.check_c);
            		currentClick3 = 1;
            	} else {
            		chk3.setImageResource(R.drawable.check);
            		currentClick3 = 0;
            	}
			}
		});
		
		check4.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
            	if( currentClick4 == 0 ) {
            		chk4.setImageResource(R.drawable.check_c);
            		currentClick4 = 1;
            	} else {
            		chk4.setImageResource(R.drawable.check);
            		currentClick4 = 0;
            	}
			}
		});

		
		checkCompleteBtn.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eventupdate();
			}
		});
		
		isDataSetting();
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void eventupdate() {
		if( currentClick1 == 0 ) {
			showToast("1 점검해주세요");
			return;
		}
		
		if( currentClick2 == 0 ) {
			showToast("2 점검해주세요");
			return;
		}
		
		if( currentClick3 == 0 ) {
			showToast("3 점검해주세요");
			return;
		}
		
		if( currentClick4 == 0 ) {
			showToast("4 점검해주세요");
			return;
		}
		
		if( checkyn.equals("Y") || checkyn.equals("y") ) {
			checkyn = "N";
		} else if( checkyn.equals("N") || checkyn.equals("n") ) {
			checkyn = "Y";
		}
		
		
		eventStartUpdate();
	}
	
	private void eventStartUpdate() {
		pShow();
		startThread(new Runnable() {
			public void run() {
				try {
					result = api.eventUpdate(getBaseContext(), workdate, app.getUseridx(), checkyn, workidx);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					public void run() {
						if(  result != null && !result.equals("") ) {
							showToast("점검결과를 저장했습니다.");
							getmActivity().finish();
							
							Intent intent = new Intent(getApplicationContext(), ListActivity.class);
							startActivity(intent);
							
							finish();
						} else {
							showToast("점검결과 저장에 실패 하였습니다.");
						}
						pHide();
					}
				});
			}
		});
	}
	
	private void isDataSetting() {
		if( checkyn.equals("Y") || checkyn.equals("y") ) {
    		chk1.setImageResource(R.drawable.check_c);
    		currentClick1 = 1;
    		chk2.setImageResource(R.drawable.check_c);
    		currentClick2 = 1;
    		chk3.setImageResource(R.drawable.check_c);
    		currentClick3 = 1;
			chk4.setImageResource(R.drawable.check_c);
    		currentClick4 = 1;
    		checkCompleteBtn.setText("점검 취소");
		}
	}
}
