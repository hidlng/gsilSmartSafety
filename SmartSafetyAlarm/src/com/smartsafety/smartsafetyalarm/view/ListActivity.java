package com.smartsafety.smartsafetyalarm.view;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.smartsafety.smartsafetyalarm.R;
import com.smartsafety.smartsafetyalarm.base.BaseActivity;
import com.smartsafety.smartsafetyalarm.model.Work;
import com.smartsafety.smartsafetyalarm.util.BackPressCloseHandler;
import com.smartsafety.smartsafetyalarm.util.ListViewAdapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListActivity extends BaseActivity {

	ArrayList<Work> list  = new ArrayList<Work>();
	MainListViewAdapter rAdapter;
	ListView listview = null;
	RelativeLayout emptylayer = null;
	
	String searchdate = "";
	
	Button logoutBtn = null;
	
	
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		backPressCloseHandler = new BackPressCloseHandler(this);
		init();
		setmActivity(this);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		listview= (ListView) findViewById(R.id.worklist);
		
		rAdapter = new MainListViewAdapter();
		listview.setAdapter(rAdapter);
		
		logoutBtn = ( Button ) findViewById(R.id.logoutBtn);
		logoutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				eventLogout();
			}
		});
		
	    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
	    Date currentTime = new Date ( );
	    searchdate = mSimpleDateFormat.format ( currentTime );
		setData();
	}

	@Override
	public void setData() {
		// TODO Auto-generated method stub
		pShow();
		startThread(new Runnable() {
			public void run() {
				try {
					list = api.worklist(getBaseContext(), searchdate, app.getSiteidx() );
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				runOnUiThread(new Runnable() {
					public void run() {
						pHide();
						if( list != null && list.size() > 0 ) {
							rAdapter.notifyDataSetChanged();
						}
					}
				});
			}
		});
	}
	
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
    
    public void eventLogout() {
		app.setLogin(false);
		app.setAutoLogin(false);
		app.setUseridx("");
		app.setSiteidx("");
		
		showToast("로그아웃 되었습니다.");
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
    }
    
	@SuppressWarnings("unused")
	private class GetDataTask extends
	AsyncTask<Void, Void, ArrayList<Work>> {

	@Override
	protected ArrayList<Work> doInBackground(
			Void... params) {
		// Simulates a background job.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			;
		}
		return list;
	}
	

	@Override
	protected void onPostExecute(ArrayList<Work> result) {
		super.onPostExecute(result);
	}
}
    
	class MainListViewAdapter extends ListViewAdapter {

		@Override
		public View setListGetView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.row_main_list,
						null);
			}
			
			
			TextView workTitleTxt = (TextView) convertView
					.findViewById(R.id.workTitleTxt);
			
			workTitleTxt.setText(list.get(position).getWorktitle());
			
			TextView workpicnameTxt = (TextView) convertView
					.findViewById(R.id.workpicnameTxt);
			
			workpicnameTxt.setText(list.get(position).getPicName());
			
			TextView workPicynTxt = (TextView) convertView
					.findViewById(R.id.workPicynTxt);
			
			String checkText = "확인전";
			
			if( list.get(position).getCheckYn().equals("Y") || list.get(position).getCheckYn().equals("y") ) {
				checkText = "확인완료";
				workPicynTxt.setTextColor(Color.BLUE);
			} else {
				workPicynTxt.setTextColor(Color.RED);
			}
			
			
			workPicynTxt.setText(checkText);
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!list.isEmpty()) {
						Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
						intent.putExtra("workidx", list.get(position).getWorkidx());
						intent.putExtra("worktitle", list.get(position).getWorktitle());
						intent.putExtra("checkyn", list.get(position).getCheckYn());
						intent.putExtra("workdate", searchdate);
						startActivity(intent);
					}
				}
			});
			
			return convertView;
		}

		@Override
		public int setCount() {
			return list.size();
		}

	}
}
